import pkg from '@aws-sdk/client-sqs';
const { SQS, PublishCommand } = pkg;
//import { SQS, PublishCommand } from "@aws-sdk/client-sqs";

const sqsClient = new SQS({ region: 'us-east-1' });
const urlSQS = "https://sqs.us-east-1.amazonaws.com/933270736007/Borrowing_Response";//"https://sqs.us-east-1.amazonaws.com/933270736007/Borrowing_Cap";

let loanData = {
      // id of loan
      loanId: "",
      // email of requesting
      email : "",
      // salary of requesting 
      salary: 0,
      // total debt of loans
      currentLoans: 0,
      // amount of loan
      amount: 0,
      // Monthly tax
      tax: 0,
      // months to deadline of payment
      term: 0,
      // generated status
      status : "",
      // quota new credit
      quota: 0,
      // avalaible Borrow Capacity
      avalaible: 0,
      // buying plan
      plan : []
};

let quotaData = {
      number : 0,
      amount : 0,
      monthQuota : 0,
      tax : 0,
      interest : 0.0
};

export const handler = async (event) => {
  let results = []
  console.log("======Event: "+ JSON.stringify(event));
  let messagesLocal = event['Records'];
  console.log("======Messages: "+ JSON.stringify(messagesLocal));

  messagesLocal.forEach(message => {
    console.log("======Message: "+ JSON.stringify(message));
    loanData = JSON.parse( message['body'] );
    console.log("======Body: "+ JSON.stringify(loanData));

    let MaxCapacity =  maxCapacity( loanData.salary ); 
    loanData.avalaible = avalaibleBorrowCap(MaxCapacity, loanData.currentLoans);
    loanData.quota = calcQuota(loanData.amount,loanData.tax, loanData.term);

    console.log("======Update 1: "+ JSON.stringify(loanData));

    loanData.status = defineStatus(loanData.quota, loanData.avalaible);

    console.log("======Update 2: "+ JSON.stringify(loanData));

    loanData = createPlan(loanData);

    console.log("======Update 3: "+ JSON.stringify(loanData));

    let params = {
      MessageBody: JSON.stringify(loanData),
      QueueUrl: urlSQS
    };

    let sendSqsMessage = sqsClient.sendMessage(params)
                                    .then((data) => {
                                      console.log(`SQS | SUCCESS: ${data.MessageId}`);
                                    })
                                    .catch((err) => {
                                      console.log(`SQS | ERROR: ${err}`);
                                    });

      results.push({
        "requestId":loanData.loanId,
        "to": loanData.email,
        "messageId":  sendSqsMessage ?? "sendMessage",
        "sent": true
      });                          
  });

  return {
    "statusCode": 200,
    "body": JSON.stringify(results)
  };
};

/**
     * Methos to calculate max capacity of borrow
     * @param {Int} salary 
     * @returns  Int max capacity of borrow
     */
function maxCapacity(salary) {
  if(!salary) 
      throw new Error("salary is required");

return  salary * 0.35;
};

/**
* Method to calculate avalaible borrow capacity
* @param {Int} maxCapacity 
* @param {Int} currentLoan 
* @returns  Int avalaible borrow capacity
*/
function avalaibleBorrowCap(maxCapacity, currentLoan) {
  console.log("======maxCapacity: "+ maxCapacity);
  console.log("======currentLoan: "+ currentLoan);
if( ! maxCapacity || !currentLoan)
  throw new Error("maxCapacity and currentLoan are required");

return maxCapacity - currentLoan;
};

/**
* Method to calculate quota value
* @param {Int} amount 
* @param {float} tax 
* @param {Int} term 
* @returns float  quota value
*/
function calcQuota(amount,tax, term){
  if(!amount || !tax || !term)
      throw new Error("amount, tax and term are required");

  tax = tax / 100;
return (amount*(1+tax)*term) - (tax*(1+tax)*term);
};

/**
* Method to define new status of loan
* @param {BigInt} quota 
* @param {BigInt} avalaibleBorrowCap 
* @returns String new status
*/
function defineStatus(quota, avalaibleBorrowCap) {
if(!quota || !avalaibleBorrowCap)
  throw new Error("quota and avalaibleBorrowCap are required");

if(quota > avalaibleBorrowCap)
  return "Rejected";

if(quota <= avalaibleBorrowCap)
  return "Approved";
};

/**
* method to create plan
* @param {Object} loanData 
* @returns Object loanData updated
*/
function createPlan (loanData) {
if(!loanData || !quotaData)
  throw new Error("loanData and quotaData are required");

console.log("======loan: "+ JSON.stringify(loanData));

 let debt = loanData.amount;
 let monthquota =  loanData.amount / loanData.term
 let tax = loanData.tax / 100;

 console.log("======vars: "+ debt +" " + monthquota+" "+ tax);

 for (let i = 1; i <= loanData.term; i++) {

  let quota = { 
      number : i, 
      amount : debt,
      monthQuota : monthquota,
      interest : debt * tax ,
      tax: tax
  };

  loanData.plan.push(quota);

  debt = debt- quota.monthQuota;
}

return loanData;
};