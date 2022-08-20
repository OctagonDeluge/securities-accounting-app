import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import axios from "axios";
import {Modal, NumberInput, Text, Title, Button} from '@mantine/core';
import {API_URL} from "../constants/API";
import {SecurityPriceChart} from "../components/charts/SecurityPriceChart";
import "../assets/styles/PortfolioSecurityInfoStyles.css"
import {IconCircleCheck, IconSquarePlus} from "@tabler/icons";
import {showNotification} from "@mantine/notifications";

export function ExchangeSecurityInfo() {
    const {secid} = useParams();
    const {exchange} = useParams();
    const {portfolioId} = useParams();
    const [opened, setOpened] = useState(false);
    const [number, setNumber] = useState(1);

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
            })
    }, [])

    const addSecurity = (number, portfolioId) => {
        const securityDTO = {
            secid: security.secid,
            name: security.name,
            quantity: number,
            purchasePrice: security.currentPrice,
            type: security.group,
            exchange: security.exchangeName,
            currency: security.currency,
            portfolioId: Number(portfolioId)
        }
        console.log(securityDTO);
        axios
            .post(`${API_URL}/security`, securityDTO)
            .then(response => {
                showNotification({
                    autoClose: 5000,
                    title: "Успех",
                    message: 'Ценная бумага добавлена в портфель',
                    color: 'green',
                    icon: <IconCircleCheck/>,
                })
            })
            .catch(response => {
                console.log(response);
            })
    }


    return (
        <div className="securityInfo">
            <IconSquarePlus onClick={() => setOpened(true)} size={32} color={'#47bf40'} className="action"/>
            <Title>{security.secid}</Title>
            <Title>{security.name}</Title>
            <div className="statistic">
                <Text size='lg'>Текущая стоимость</Text>
                <Text size='xl'>{security.currentPrice} {security.currency}</Text>
            </div>
            <SecurityPriceChart entity={security}/>
            <Modal opened={opened} onClose={() => setOpened(false)} title='Укажите количество ценных бумаг'>
                <NumberInput
                    value={number}
                    onChange={(val) => setNumber(val)}
                    label="Количество ценных бумаг"
                    description="Минимальное количество - 1"
                    min={1}
                />
                <Button component="button" onClick={() => addSecurity(number, portfolioId)}>Добавить</Button>
            </Modal>
        </div>
    )
}