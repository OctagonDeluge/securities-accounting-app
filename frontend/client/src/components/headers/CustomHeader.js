import {Anchor, Burger, createStyles} from "@mantine/core";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {Wallet} from "../elements/Wallet";
import {AvatarMenu} from "../elements/AvatarMenu";
import "../../assets/styles/CustomHeaderStyles.css"
import {useMediaQuery} from "react-responsive";

export function CustomHeader({links, service, service1}) {
    const [active, setActive] = useState(links[0].link);
    const navigate = useNavigate();
    const isMobile = useMediaQuery({ query: '(max-width: 600px)' })

    const items = links.map((link) => (
        <Anchor
            key={link.label}
            className="link"
            onClick={(event) => {
                event.preventDefault();
                setActive(link.link);
                navigate(link.link);
            }}
        >
            {link.label}
        </Anchor>
    ));


    return (
        <div className="header">
            {
                isMobile ?
                    <Burger opened/>
                    :
                    <div className="tabs">
                        {items}
                    </div>
            }
            <div className="info">
                <Wallet state={service.wallet}/>
                <AvatarMenu service={service1}/>
            </div>
        </div>
    )
}