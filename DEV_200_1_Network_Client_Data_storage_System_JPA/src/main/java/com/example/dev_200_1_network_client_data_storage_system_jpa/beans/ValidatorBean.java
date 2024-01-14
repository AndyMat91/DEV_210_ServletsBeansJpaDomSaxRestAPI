package com.example.dev_200_1_network_client_data_storage_system_jpa.beans;

import com.example.dev_200_1_network_client_data_storage_system_jpa.enumeration.ErrorFieldType;
import com.example.dev_200_1_network_client_data_storage_system_jpa.enumeration.ErrorReasonType;
import com.example.dev_200_1_network_client_data_storage_system_jpa.service.AddressService;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.servlet.ServletException;

import java.io.IOException;
import java.util.regex.Pattern;

@Stateless
public class ValidatorBean {

    @EJB
    private ReturningErrors returningErrors;

    @EJB
    private AddressService addressService;

    public boolean validateClientList(String name, String type) throws ServletException, IOException {
        if (validateClient(name, 100, ErrorFieldType.CLIENT_NAME.getType(), true, false) &&
                validateClient(type, 20, ErrorFieldType.CLIENT_TYPE.getType(), false, true)) return true;
        return false;
    }

    public boolean validateAddressListWithMac(String ip, String mac, String model, String address) throws ServletException, IOException {
        if (validateAddress(ip, 25, ErrorFieldType.CLIENT_IP.getType(), true, false) &&
                validateAddress(mac, 20, ErrorFieldType.CLIENT_MAC.getType(), false, true) &&
                validateAddress(model, 100, ErrorFieldType.CLIENT_MODEL.getType(), false, false) &&
                validateAddress(address, 200, ErrorFieldType.CLIENT_ADDRESS.getType(), false, false)) return true;
        return false;
    }
    public boolean validateAddressListNotMac(String ip, String model, String address) throws ServletException, IOException {
        if (validateAddress(ip, 25, ErrorFieldType.CLIENT_IP.getType(), true, false) &&
                validateAddress(model, 100, ErrorFieldType.CLIENT_MODEL.getType(), false, false) &&
                validateAddress(address, 200, ErrorFieldType.CLIENT_ADDRESS.getType(), false, false)) return true;
        return false;
    }

    public boolean validateClient(String data, int maxLength, String errorField, boolean isName, boolean isType) throws IOException {
        if (validateIsEmpty(data, errorField) && validateLength(data, maxLength, errorField)) {
            if (isName) {
                if (!validateClientName(data)) {
                    returningErrors.setErrorFieldType(errorField);
                    returningErrors.setErrorReasonType(ErrorReasonType.ERROR_CLIENTNAME.getType());
                    return false;
                }
            }
            if (isType) {
                if (!(data.equals("Юридическое лицо") || data.equals("Физическое лицо"))) {
                    returningErrors.setErrorFieldType(errorField);
                    returningErrors.setErrorReasonType(ErrorReasonType.ERROR_TYPE.getType());
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean validateAddress(String data, int maxLength, String errorField, boolean isIp, boolean isMac) throws IOException {

        if (validateIsEmpty(data, errorField) && validateLength(data, maxLength, errorField)) {
            if (isIp) {
                if (!validateIp(data)) {
                    returningErrors.setErrorFieldType(errorField);
                    returningErrors.setErrorReasonType(ErrorReasonType.ERROR_IPMASK.getType());
                    return false;
                }
            } else if (isMac) {
                if (addressService.findByAddressMac(data) != null) {
                    returningErrors.setErrorFieldType(errorField);
                    returningErrors.setErrorReasonType(ErrorReasonType.ERROR_MAC.getType());
                    return false;
                }
                if (!validateMac(data)) {
                    returningErrors.setErrorFieldType(errorField);
                    returningErrors.setErrorReasonType(ErrorReasonType.ERROR_MACMASK.getType());
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean validateLength(String data, int maxLength, String errorField) {
        if (data.length() > maxLength) {
            returningErrors.setErrorFieldType(errorField);
            returningErrors.setErrorReasonType(ErrorReasonType.ERROR_LENGTH.getType());
            return false;
        }
        return true;
    }

    public boolean validateIp(String ip) {
        Pattern PATTERN = Pattern.compile("(([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])[.]){3}([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])");
        return PATTERN.matcher(ip).matches();
    }

    public boolean validateMac(String mac) {
        Pattern PATTERN = Pattern.compile("([0-9a-zA-Z]{2}[-]){5}[0-9a-zA-Z]{2}");
        return PATTERN.matcher(mac).matches();
    }

    public boolean validateIsEmpty(String data, String errorField) throws IOException {
        if (data.equals("")) {
            returningErrors.setErrorFieldType(errorField);
            returningErrors.setErrorReasonType(ErrorReasonType.ERROR_EMPTY.getType());
            return false;
        }
        return true;
    }

    public boolean validateClientName(String name) {
        Pattern PATTERN = Pattern.compile("[а-яА-Я-,. ]+");
        return PATTERN.matcher(name).matches();
    }
}
