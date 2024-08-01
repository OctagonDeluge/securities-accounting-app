import React, {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {axios} from "../api/axios";
import {Button, Modal, NumberInput, Table, Text, Title} from '@mantine/core';
import {API_URL} from "../constants/API";
import {SecurityPriceChart} from "../components/charts/SecurityPriceChart";
import "../assets/styles/PortfolioSecurityInfoStyles.css"
import {TradingRequests} from "../api/service/TradingRequests";
import {PaymentCard} from "../components/cards/PaymentCard";

export function ExchangeSecurityInfo({state}) {
    const {secid} = useParams();
    const {exchange} = useParams();
    const {portfolioId} = useParams();
    const navigate = useNavigate();
    const [opened, setOpened] = useState(false);
    const [number, setNumber] = useState(1);
    const [payments, setPayments] = useState([]);
    const [error, setError] = useState(false);
    const service = TradingRequests();

    const [security, setSecurity] = useState({
        secid: "",
        name: "",
        currentPrice: 0,
        group: "",
        exchangeName: "",
        currency: ""
    });

    useEffect(() => {
        axios
            .get(`${API_URL}/exchange/${exchange}/security/${secid}`)
            .then(response => {
                setSecurity(response.data);
                loadPayments(response.data.exchangeName, response.data.secid);
            })
            .catch(() => {
                setError(true);
            })
    }, [])

    const loadPayments = (exchangeName, secid) => {
        axios
            .get(`${API_URL}/payment/exchange/${exchangeName}/security/${secid}`)
            .then(response => {
                setPayments(response.data);
            });
    }

    const addSecurity = (number, portfolioId) => {
        const securityDTO = {
            secid: security.secid,
            name: security.name,
            quantity: number,
            purchasePrice: security.currentPrice,
            type: security.group,
            exchange: security.exchangeName,
            currency: security.currency,
            portfolioId: Number(portfolioId),
            walletId: JSON.parse(localStorage.getItem("wallet")).id
        }

        service.buySecurity(securityDTO, state);
    }


    return (
        <div className="securityInfo">
            <Button className="action" onClick={() => setOpened(true)}>Купить</Button>
            <Title>{security.secid}</Title>
            <Title>{security.name}</Title>
            <div className="statistic">
                <Text size='lg'>Текущая стоимость</Text>
                <Text size='xl'>{security.currentPrice} {security.currency}</Text>
            </div>
            {error ? navigate(`/portfolio/${portfolioId}/exchange`) :
                <>
                    <SecurityPriceChart entity={security}/>
                    {security.group === "stock_shares" ? <Title order={4}>Дивиденды</Title> :
                        <Title order={4}>Купоны</Title>}
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
                        <NumberInput
                            style={{paddingBottom: 20}}
                            value={number}
                            onChange={(val) => setNumber(val)}
                            label="Количество ценных бумаг"
                            description="Минимальное количество - 1"
                            min={1}
                        />
                        <Button component="button" onClick={() => {
                            addSecurity(number, portfolioId);
                            setOpened(false)
                        }}>Добавить</Button>
                    </Modal>
                </>
            }
        </div>
    )
}