import {Anchor, Button, Paper, PasswordInput, Text, TextInput, Title,} from '@mantine/core';
import {useStyles} from "../assets/styles/AuthStyles";
import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import {useSigninRequest} from "../api/service/AuthRequests";

export function Signin({auth, wallet}) {
    const {classes} = useStyles();
    const navigate = useNavigate();
    const [credentials, setCredentials] = useState({
        email: "",
        password: ""
    })
    const request = useSigninRequest();

    useEffect(() => {
        let user = {
            name: request.data.name,
            username: request.data.username,
            level: request.data.level
        }
        wallet.setWallet(request.data.wallet);
        localStorage.setItem("wallet", JSON.stringify(request.data.wallet));
        localStorage.setItem("user", JSON.stringify(user));
        if(request.data.username !== "") {
            localStorage.setItem("auth", true);
            auth.setAuth(true);
            navigate("/portfolio");
        }
    }, [request.data]);
    const handleLogin = () => {
        request.send(credentials);
    }

    const handleChange = (e) => {
        const value = e.target.value;
        setCredentials({...credentials, [e.target.name]: value})
    }

    return (
        <div className={classes.wrapper}>
            <Paper className={classes.form} radius={0}>
                <Title align="center" order={2} className={classes.title} mt="md" mb={50}>
                    Время-деньги!
                </Title>
                <TextInput label="Email адрес" placeholder="hello@gmail.com" name="email" value={credentials.email} size="md" onChange={event => handleChange(event)}/>
                <PasswordInput label="Пароль" placeholder="Password" name="password" value={credentials.password} mt="md" size="md" onChange={event => handleChange(event)}/>
                <Button fullWidth mt="xl" size="md" component="button" onClick={() => handleLogin()}>
                    Войти
                </Button>

                <Text align="center" mt="md">
                    Нет аккаунта? {' '}
                    <Anchor href="/signup" weight={700}>
                        Регистрация
                    </Anchor>
                </Text>
            </Paper>
        </div>
    );
}