import {createStyles} from "@mantine/core";
export const useStyles = createStyles((theme) => ({
    wrapper: {
        display: "flex",
        justifyContent: "center",
        height: "90%"
    },

    form: {
        display: "flex",
        justifyContent: "space-between",
        flexDirection: "column",
        marginTop: "10%",
        height: "65%",
        padding: 25,
        borderRadius: 5,
        backgroundColor: "#50505052",
        borderRight: `${16} solid ${
            theme.colorScheme === 'dark' ? theme.colors.dark[7] : theme.colors.gray[3]
        }`,
        width: 450,
    },

    title: {
        color: theme.colorScheme === 'dark' ? theme.white : theme.black,
        fontFamily: `Greycliff CF, ${theme.fontFamily}`,
    },
}));