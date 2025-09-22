import os
import boto3
import json

def send_sns(message, subject):
    client = boto3.client("sns")
    topic_arn = "arn:aws:sns:us-east-2:933270736007:CrediYA_Estado"
    client.publish(
        TopicArn=topic_arn, Message=message, Subject=subject)

def lambda_handler(event, context):
    results = []
    print(event)
    for record in event.get("Records", []):
        try:
            print(record)
            print(record["body"])
            body = json.loads(record["body"])
            print(body)

            email = body.get("email") or body.get("clientEmail") or body.get("correo")
            id = body.get("id", "a21f-152d-1fc8ba35-1d4d1")
            status = body.get("status", "None")

            messageText = f"Hola, \n Tu credito con id: "+id+" ha sido "+ status +"\n  Un Saludo,\n Team CrediYA";
            subject = "Estado de tu credito"
            
            print(messageText)

            response = send_sns(messageText, subject)

            print(response)

            if response is None:
                idmessage = "Null Response"
            else:
                idmessage = response.get("MessageId", "bad")

            print(idmessage)

            results.append({
                "requestId":id,
                "to": email,
                "messageId":  idmessage,
                "sent": True
            })

        except Exception as e:
            results.append({"error": True, "message": str(e)})

    return {
        "statusCode": 200,
        "body": json.dumps(results, indent=2)
    }