package com.solidus_snake.best.umeal.JSONBuilder;

//вспомогательный интерфейс для чтения объектов из файлы json
public abstract class JSONBuilder<A>{

    public abstract A getObjectFromJSON();
}
