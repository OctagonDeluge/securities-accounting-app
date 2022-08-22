import {useNavigate, useParams} from "react-router-dom";
import axios from "axios";
import {API_URL} from "../constants/API";
import React, {useEffect, useState} from "react";
import {SecurityPriceChart} from "../components/charts/SecurityPriceChart";
import {Title, Text, Table, ScrollArea} from "@mantine/core";
import "../assets/styles/PortfolioSecurityInfoStyles.css"
import {PurchaseInfoCard} from "../components/cards/PurchaseInfoCard";
import {IconCircleCheck, IconSquareX} from "@tabler/icons";
import {showNotification} from "@mantine/notifications";
import {PaymentCard} from "../components/cards/PaymentCard";

export function PortfolioSecurityInfo() {
    const {securityId} = useParams();
    const [security, setSecurity] = useState({
        id: "",
        secid: "",
        name: "",
        totalCost: "",
        profit: "",
        group: "",
        exchangeName: "",
        currency: "",
        purchaseInfos: []
    });
    const [purchaseInfos, setPurchaseInfos] = useState([]);
    const [payments, setPayments] = useState([]);
    const [deleted, setDeleted] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        loadSecurity();
    }, []);

    useEffect(() => {
        if(deleted) {
            navigate(-1);
        }
    }, [deleted]);

    const loadSecurity = () => {
        axios
            .get(`${API_URL}/security/${securityId}`)
            .then(response => {
                setSecurity(response.data);
                loadPurchaseInfos(response.data);
                loadPayments(response.data.exchangeName, response.data.secid);
            })
    }

    const loadPurchaseInfos = (security) => {
        let ids = [];
        security.purchaseInfos.map(object => ids.push(object.id));
        axios
            .get(`${API_URL}/purchaseInfo`, {
                params: {
                    purchaseIds: ids.toString()
                }
            })
            .then(response => {
                setPurchaseInfos(response.data);
            });
    }

    const loadPayments = (exchangeName, secid) => {
        axios
            .get(`${API_URL}/payment/exchange/${exchangeName}/security/${secid}`)
            .then(response => {
                setPayments(response.data);
            });
    }

    const deleteSecurity = (id) => {
        axios
            .delete(`${API_URL}/security/${id}`)
            .then(response => {
                showNotification({
                    autoClose: 5000,
                    title: "Успех",
                    message: 'Ценная бумага удалена из портфеля',
                    color: 'green',
                    icon: <IconCircleCheck/>,
                })
                setDeleted(true);
            })
            .catch(reason => {
                console.log(reason);
            })
    }

    return (
        <div className="securityInfo">
            <IconSquareX onClick={() => deleteSecurity(security.id)} size={32} color={'#fc0202'} className="action"/>
            <Title>{security.secid}</Title>
            <Title>{security.name}</Title>
            <div className="statistic">
                <Text size='lg'>Общая стоимость</Text>
                <Text size='xl'>{security.totalCost} {security.currency}</Text>
                <Text size='lg'>Доход от текущей стоимости</Text>
                {security.profit >= 0 ?
                    <Text size='xl' color="#008000">+{security.profit} {security.currency}</Text>
                    :
                    <Text size='xl' color="red">-{security.profit} {security.currency}</Text>}
            </div>
            <SecurityPriceChart entity={security}/>
            <Title order={4}>История</Title>
            <ScrollArea>
                <Table highlightOnHover={true} sx={{minWidth: 800}} verticalSpacing="xs">
                    <thead>
                    <tr>
                        <th>Дата</th>
                        <th>Сумма</th>
                        <th>Количество</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        purchaseInfos.map(purchase => (
                            <PurchaseInfoCard key={purchase.id} purchase={purchase}/>
                        ))
                    }
                    </tbody>
                </Table>
            </ScrollArea>
            {security.group === "shares" ? <Title order={4}>Дивиденды</Title> : <Title order={4}>Купоны</Title>}
            <ScrollArea>
                <Table highlightOnHover={true} sx={{minWidth: 800}} verticalSpacing="xs">
                    <thead>
                    <tr>
                        <th>Дата</th>
                        <th>Сумма</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        payments.map(payment => (
                            <PaymentCard key={payment.paymentDate} payment={payment}/>
                        ))
                    }
                    </tbody>
                </Table>
            </ScrollArea>
        </div>
    )
}