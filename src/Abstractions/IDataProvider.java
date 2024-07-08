package Abstractions;

import java.util.List;

public interface IDataProvider<TData> {
    TData Get(Integer id);

    List<TData> GetAll();
}
