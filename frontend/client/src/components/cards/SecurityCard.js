import { createStyles, Group, Paper, Text } from '@mantine/core';
import {
    IconCoin,
    IconArrowUpRight,
    IconArrowDownRight,
} from '@tabler/icons';
import {useNavigate, useParams} from "react-router-dom";

const useStyles = createStyles((theme) => ({
    root: {
        margin: 20
    },
    value: {
        fontSize: 24,
        fontWeight: 700,
        lineHeight: 1,
    },

    diff: {
        lineHeight: 1,
        display: 'flex',
        alignItems: 'center',
    },

    icon: {
        color: theme.colorScheme === 'dark' ? theme.colors.dark[3] : theme.colors.gray[4],
    },

    title: {
        fontWeight: 700,
        textTransform: 'uppercase',
    },
}));


export function SecurityCard({security}) {
    const { classes } = useStyles();
    const DiffIcon = security.profit >= 0 ? IconArrowUpRight : IconArrowDownRight;
    const navigate = useNavigate();
    const {portfolioId} = useParams();

    const navigateToSecurityInfoPage = () => {
        navigate(`/portfolio/${portfolioId}/security/${security.id}`)
    }

    return (
        <div className={classes.root} onClick={() => navigateToSecurityInfoPage()}>
            <Paper withBorder p="md" radius="md" key={security.name}>
                <Group position="apart">
                    <Text size="xs" color="dimmed" className={classes.title}>
                        {security.name}
                    </Text>
                    <IconCoin className={classes.icon} size={22} stroke={1.5} />
                </Group>

                <Group align="flex-end" spacing="xs" mt={25}>
                    <Text className={classes.value}>{parseFloat(security.totalCost.toFixed(2))} {security.currency}</Text>
                    <Text
                        color={security.profit >= 0 ? 'teal' : 'red'}
                        size="sm"
                        weight={500}
                        className={classes.profit}
                    >
                        <span>{security.profit.toFixed(2)} {security.currency}</span>
                        <DiffIcon size={16} stroke={1.5} />
                    </Text>
                </Group>

                <Text size="xs" color="dimmed" mt={7}>
                    В сравнении с текущей ценой
                </Text>
            </Paper>
        </div>
    );
}