import {axios} from "../axios";
import {API_URL} from "../../constants/API";
import {showNotification} from "@mantine/notifications";
import {IconCircleCheck, IconCircleX} from "@tabler/icons";
import React from "react";

export function TradingRequests() {
    const buySecurity = (purchaseRequest, service) => {
        axios
            .post(`${API_URL}/trade/security`, purchaseRequest)
            .then(response => {
                service.setBalance(response.data.walletBalance);
                showNotification({
                    autoClose: 5000,
                    title: "Успех",
                    message: 'Ценная бумага добавлена в портфель',
                    color: 'green',
                    icon: <IconCircleCheck/>,
                })
            })
            .catch(response => {
                showNotification({
                    autoClose: 5000,
                    title: "Ошибка",
                    message: response,
                    color: 'red',
                    icon: <IconCircleX/>,
                })
            })
    }

    const sellSecurity = (saleRequest, service) => {
        axios({
            method: "delete",
            url: `${API_URL}/trade/security`,
            data: saleRequest
        })
            .then(response => {
                service.setBalance(response.data.walletBalance);
                showNotification({
                    autoClose: 5000,
                    title: "Успех",
                    message: 'Ценная бумага продана',
                    color: 'green',
                    icon: <IconCircleCheck/>,
                })
            })
            .catch(response => {
                showNotification({
                    autoClose: 5000,
                    title: "Ошибка",
                    message: response,
                    color: 'red',
                    icon: <IconCircleX/>,
                })
            })
    }

    return { buySecurity, sellSecurity };
}