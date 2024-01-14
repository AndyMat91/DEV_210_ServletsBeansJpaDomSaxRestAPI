package com.example.dev_200_1_network_client_data_storage_system_jpa.enumeration;

public enum ErrorReasonType {
    ERROR_EMPTY(" - поле не может быть пустым!"),
    ERROR_LENGTH(" - поле превышает допустимую длину"),
    ERROR_IPMASK(" - поле не соответвует маски IP"),
    ERROR_MACMASK(" - поле не соответвует маски MAC"),
    ERROR_MAC(" - данный MAC-адрес уже есть в базе данных"),
    ERROR_TYPE(" - поле содержит недопустимое значение"),
    ERROR_CLIENTNAME(" - поле содержит недопустимый алфавит или символы");

    private String type;

    ErrorReasonType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

