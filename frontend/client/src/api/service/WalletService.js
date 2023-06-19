import {useState} from "react";

export function useWalletService() {
    const [wallet, setWallet] = useState(JSON.parse(localStorage.getItem("wallet")) || {
        id:0,
        name: "",
        balance: 0,
        currency: ""
    });

    const setBalance = (value) => {
        setWallet({...wallet, balance: value});
        localStorage.setItem("wallet", JSON.stringify({...wallet, balance: value}))
    }

    return {wallet, setWallet, setBalance}
}