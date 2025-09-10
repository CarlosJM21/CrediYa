package co.com.mrcompany.model.generics;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Page <T>{
    public List<T> items;
    public Integer size;
    public Integer page;
    public Long totalItems;

    public void dataPagination(Integer size, Integer page, Long items)
    {
        this.page = page;
        this.size = size;
        this.totalItems = items;
    }
}
