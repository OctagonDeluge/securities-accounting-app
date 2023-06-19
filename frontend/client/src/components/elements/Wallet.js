import {Avatar, Text} from "@mantine/core";
import React from "react";
import "../../assets/styles/WalletStyles.css"

export function Wallet({state}) {

    return (
        <div className="wallet">
            <Avatar src="https://img.icons8.com/stickers/100/null/wallet.png" radius="xl"/>
            <Text>{state.name} {state.balance} {state.currency}</Text>
        </div>
    )
}