import {useNavigate, useParams} from "react-router-dom";
import {axios} from "../api/axios";
import {API_URL} from "../constants/API";
import React, {useEffect, useState} from "react";
import {SecurityPriceChart} from "../components/charts/SecurityPriceChart";
import {Button, Modal, NumberInput, Table, Text, Title} from "@mantine/core";
import "../assets/styles/PortfolioSecurityInfoStyles.css"
import {PurchaseInfoCard} from "../components/cards/PurchaseInfoCard";
import {IconQuestionMark, IconRefresh} from "@tabler/icons";
import {PaymentCard} from "../components/cards/PaymentCard";
import {TradingRequests} from "../api/service/TradingRequests";
import {useExchangePrices} from "../api/service/ExchangeRequests";

export function PortfolioSecurityInfo({state}) {
    const {securityId} = useParams();
    const [security, setSecurity] = useState({
        id: 0,
        secid: "",
        name: "",
        totalCost: 0,
        profit: 0,
        group: "",
        exchangeName: "",
        currency: "",
        purchaseInfos: []
    });
    const [purchaseInfos, setPurchaseInfos] = useState([]);
    const [payments, setPayments] = useState([]);
    const [opened, setOpened] = useState(false);
    const [deleted, setDeleted] = useState(false);
    const [number, setNumber] = useState(1);
    const service = TradingRequests();
    const exchangeService = useExchangePrices();
    const navigate = useNavigate();

    useEffect(() => {
        loadSecurity();
    }, []);

    useEffect(() => {
        if (deleted) {
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
        axios
            .get(`${API_URL}/purchaseInfo/security/${security.id}`)
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

    const sellSecurity = () => {
        let saleRequest = {
            securityId: security.id,
            price: exchangeService.price,
            quantity: number,
            walletId: state.wallet.id
        }
        service.sellSecurity(saleRequest, state);
    }

    return (
        <div className="securityInfo">
            <Button className="action" onClick={() => {
                setOpened(true);
                exchangeService.request(security.secid, security.group)
            }}>Продать</Button>
            <Title>{security.secid}</Title>
            <Title>{security.name}</Title>
            <div className="statistic">
                <Text size='lg'>Общая стоимость</Text>
                <Text size='xl'>{security.totalCost} {security.currency}</Text>
                <Text size='lg'>Доход от текущей стоимости</Text>
                {security.profit >= 0 ?
                    <Text size='xl' color="#008000">+{security.profit} {security.currency}</Text>
                    :
                    <Text size='xl' color="red">{security.profit} {security.currency}</Text>}
            </div>
            <SecurityPriceChart entity={security}/>
            <Title order={4}>История</Title>
            <Table highlightOnHover={true} sx={{width: "40%"}} verticalSpacing="xs">
                <thead>
                <tr>
                    <th>Цена</th>
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
            {security.group === "shares" ? <Title order={4}>Дивиденды</Title> : <Title order={4}>Купоны</Title>}
            <Table highlightOnHover={true} sx={{width: "40%"}} verticalSpacing="xs">
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
            <Modal opened={opened} onClose={() => setOpened(false)} title='Укажите количество ценных бумаг'>
                <Text>Цена продажи составит {exchangeService.price}</Text>
                <IconRefresh style={{cursor:"pointer"}}
                             onClick={() => exchangeService.request(security.secid, security.group)}/>
                <NumberInput
                    style={{paddingBottom: 20}}
                    value={number}
                    onChange={(val) => setNumber(val)}
                    label="Количество ценных бумаг"
                    description="Минимальное количество - 1"
                    min={1}
                />
                <Button component="button" onClick={() => {
                    sellSecurity();
                    setOpened(false)
                }}>Продать</Button>
            </Modal>
        </div>
    )
}