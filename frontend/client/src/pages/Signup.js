import {Anchor, Avatar, Button, Group, Paper, PasswordInput, Select, Text, TextInput, Title,} from '@mantine/core';
import {useStyles} from "../assets/styles/AuthStyles";
import React, {forwardRef, useState} from "react";
import {useNavigate} from "react-router-dom";
import {useSignupRequest} from "../api/service/AuthRequests";
import {showNotification} from "@mantine/notifications";
import {IconAlertCircle} from "@tabler/icons";

export function Signup() {
    const {classes} = useStyles();
    const navigate = useNavigate();
    const [signupRequestData, setSignupRequestData] = useState({
        name: "",
        email: "",
        level: "",
        password: "",
        repeatPassword: ""
    })
    const service = useSignupRequest(signupRequestData);
    const data = [
        {
            image: "https://img.icons8.com/color/96/stack-of-money.png",
            label: "Легко",
            value: "EASY",
            description: "Стартовый капитал составит 500 000 RUB"
        },
        {
            image: "https://img.icons8.com/color/96/cash.png",
            label: "Средне",
            value: "NORMAL",
            description: "Стартовый капитал составит 60 000 RUB"
        },
        {
            image: "https://img.icons8.com/parakeet/96/null/coins--v2.png",
            label: "Сложно",
            value: "HARD",
            description: "Стартовый капитал составит 20 000 RUB"
        },
        {
            image: "https://img.icons8.com/color/96/money-bag.png",
            label: "Инвестор",
            value: "INVESTOR",
            description: "Стартовый капитал составит 2 000 000 RUB"
        }
    ];

    const signup = () => {
        if(signupRequestData.password === signupRequestData.repeatPassword){
            service.request();
        } else {
            showNotification({
                autoClose: 5000, title: "Ошибка", message: "Пароли не идентичны", color: 'red', icon: <IconAlertCircle/>,
            });
        }
    }

    const handleChange = (e) => {
        const value = e.target.value;
        setSignupRequestData({...signupRequestData, [e.target.name]: value})
    }

    interface ItemProps {
        image: string;
        label: string;
        description: string;
    }

    const SelectItem = forwardRef(
        ({image, label, description, ...others}: ItemProps, ref) => (
            <div ref={ref} {...others}>
                <Group noWrap>
                    <Avatar src={image}/>

                    <div>
                        <Text size="sm">{label}</Text>
                        <Text size="xs" opacity={0.65}>
                            {description}
                        </Text>
                    </div>
                </Group>
            </div>
        )
    );

    return (
            <div className={classes.wrapper}>
                <Paper className={classes.form}>
                    <Title order={2} className={classes.title}>
                        Заполните форму
                    </Title>
                    <TextInput required={true} name="name" value={signupRequestData.name} label="Имя"
                               placeholder="Ваше имя"
                                onChange={event => handleChange(event)}/>
                    <TextInput required={true} name="email" value={signupRequestData.email} label="Email адрес"
                               placeholder="hello@gmail.com" onChange={event => handleChange(event)}/>
                    <Select
                        itemComponent={SelectItem}
                        label="Сложность"
                        placeholder="Выберите сложность"
                        data={data}
                        onChange={event => setSignupRequestData({...signupRequestData, level: event})}
                    />
                    <PasswordInput required={true} name="password" value={signupRequestData.password} label="Пароль"
                                   placeholder="Пароль" onChange={event => handleChange(event)}/>
                    <PasswordInput required={true} name="repeatPassword" value={signupRequestData.repeatPassword} label="Повторите пароль"
                                   placeholder="Пароль" onChange={event => handleChange(event)}/>

                    <Button component="button"
                            onClick={() => signup()}>
                        Регистрация
                    </Button>

                    <Text align="center">
                        Уже есть аккаунт? {' '}
                        <Anchor href="/signin" weight={700}>
                            Авторизация
                        </Anchor>
                    </Text>
                </Paper>
            </div>
    );
}