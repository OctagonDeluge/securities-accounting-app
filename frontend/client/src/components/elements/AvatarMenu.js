import {Avatar, Menu} from "@mantine/core";
import {IconLogout, IconUserCircle} from "@tabler/icons";
import {useSignoutRequest} from "../../api/service/AuthRequests";
import {useNavigate} from "react-router-dom";

export function AvatarMenu({service}) {
    const signout = useSignoutRequest();
    const navigate = useNavigate();
    return (
        <Menu shadow="md" width={200}>
            <Menu.Target>
                <Avatar style={{cursor: "pointer"}} color="cyan" size="md" radius="xl">{JSON.parse(localStorage.getItem("user")).name.charAt(0)}</Avatar>
            </Menu.Target>

            <Menu.Dropdown>
                <Menu.Label>Аккаунт</Menu.Label>
                <Menu.Item onClick={() => navigate("/profile")} icon={<IconUserCircle size={14} />}>Профиль</Menu.Item>
                <Menu.Item onClick={() => {service.setAuth(false); signout.request()}} icon={<IconLogout size={14} />}>Выход</Menu.Item>
            </Menu.Dropdown>
        </Menu>
    );
}