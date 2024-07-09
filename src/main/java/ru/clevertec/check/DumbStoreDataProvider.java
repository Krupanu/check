//package main.java.ru.clevertec.check;
//
//
//import Abstractions.IDataProvider;
//
//import java.util.HashMap;
//import java.util.List;
//
//public class DumbStoreDataProvider implements IDataProvider<Item> {
//
//    private final HashMap<Integer, Item> _items;
//
//    public DumbStoreDataProvider() {
//        _items = new HashMap<>();
//        _items.put(1, new Item(1, "test", 5.0, false));
//        _items.put(2, new Item(2, "test 2", 1.0, true));
//        _items.put(3, new Item(3, "test 3", 9.0, false));
//        _items.put(4, new Item(4, "test 4", 10.0, true));
//    }
//
//    @Override
//    public Item Get(Integer id) {
//        return _items.get(id);
//    }
//
//    @Override
//    public List<Item> GetAll() {
//        return _items.values().stream().toList();
//    }
//}
