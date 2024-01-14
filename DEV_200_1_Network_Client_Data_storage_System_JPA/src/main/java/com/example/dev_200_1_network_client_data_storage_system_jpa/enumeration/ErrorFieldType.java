package com.example.dev_200_1_network_client_data_storage_system_jpa.enumeration;

public enum ErrorFieldType {
    CLIENT_NAME("Наименование клиента"),
    CLIENT_TYPE("Тип клиента"),
    CLIENT_IP("Сетевой адрес устройства (IP)"),
    CLIENT_MAC("Физический адрес устройства (MAC)"),
    CLIENT_MODEL("Модель устройства"),
    CLIENT_ADDRESS("Адрес места нахождения");

    private String type;

    ErrorFieldType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
