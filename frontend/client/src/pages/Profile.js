import React, {useState} from "react";
import {Button, PasswordInput, Title} from "@mantine/core";
import "../assets/styles/ProfileStyles.css"
import {axios} from "../api/axios";
import {API_URL} from "../constants/API";
import {showNotification} from "@mantine/notifications";
import {IconCircleCheck, IconX} from "@tabler/icons";

export function Profile() {
    const [oldPassword, setOldPassword] = useState("");
    const [newPassword, setNewPassword] = useState("");

    const request = () => {
        const changeRequest = {
            oldPassword: oldPassword,
            newPassword: newPassword
        }

        axios
            .post(`${API_URL}/profile/change`, changeRequest)
            .then(response => {
                showNotification({
                    autoClose: 5000,
                    title: "Успех",
                    message: 'Пароль изменен',
                    color: 'green',
                    icon: <IconCircleCheck/>,
                })
            })
            .catch(reason => {
                showNotification({
                    autoClose: 5000,
                    title: "Ошибка",
                    message: "Пароль не был изменен",
                    color: "red",
                    icon: <IconX/>,
                })
            })
    }

    return (
        <div className="profile">
            <div className="sub-profile">
                <Title order={2}>Изменить пароль</Title>
                <PasswordInput label="Старый пароль" placeholder="Password" name="password" value={oldPassword} mt="md" size="md"
                               onChange={event => {setOldPassword(event.target.value)}}/>
                <PasswordInput label="Новый пароль" placeholder="Password" name="password" value={newPassword} mt="md" size="md"
                               onChange={event => {setNewPassword(event.target.value)}}/>
                <Button onClick={() => request()} style={{marginTop: 20}}>Отправить</Button>
            </div>
        </div>
    )
}