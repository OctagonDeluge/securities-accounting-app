import "../../assets/styles/PortfolioCardStyles.css"
import {IconSquarePlus} from "@tabler/icons";
import {Button, Modal, TextInput} from "@mantine/core";
import React, {useState} from "react";
import {useForm} from "@mantine/form";

export function PortfolioAddCard({createPortfolio}) {
    const [opened, setOpened] = useState(false);
    const form = useForm({
        initialValues: {
            title: "",
        },

        validate: {
            title: (value) => (value.length < 3 ? "Название портфеля должно состоять хотя бы из 3 символов" : null),
        },
    });

    return (
        <>
            <div className="portfolio" style={{marginTop: 10}} onClick={() => setOpened(true)}>
                <IconSquarePlus color="green" size={28} style={{marginRight: 10}}/>
                <div className="name">
                    Добавить портфель
                </div>
            </div>
            <Modal opened={opened} onClose={() => {setOpened(false); form.reset()}} title="Введите название портфеля">
                <form onSubmit={form.onSubmit(() => {
                    createPortfolio(form.values.title)
                    setOpened(false)
                    form.reset();
                })}>
                    <TextInput style={{marginBottom: 10}} label="Название портфеля" placeholder="Название" {...form.getInputProps('title')} />
                    <Button type="submit">
                        Добавить
                    </Button>
                </form>
            </Modal>
        </>
    )
}