import {useState} from 'react';
import {createStyles, Header, Group, ActionIcon, Container, Burger, Anchor} from '@mantine/core';
import {useDisclosure} from '@mantine/hooks';
import {
    IconBrandGithub,
    IconBrandAmongus,
    IconBrandTelegram
} from '@tabler/icons';
import {useNavigate} from "react-router-dom";
import {contacts} from "../../constants/AuthorContacts";

const useStyles = createStyles((theme) => ({
    inner: {
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center',
        height: 56,

        [theme.fn.smallerThan('sm')]: {
            justifyContent: 'flex-start',
        },
    },

    links: {
        width: 260,

        [theme.fn.smallerThan('sm')]: {
            display: 'none',
        },
    },

    social: {
        width: 260,

        [theme.fn.smallerThan('sm')]: {
            width: 'auto',
            marginLeft: 'auto',
        },
    },

    burger: {
        marginRight: theme.spacing.md,

        [theme.fn.largerThan('sm')]: {
            display: 'none',
        },
    },

    link: {
        display: 'block',
        lineHeight: 1,
        padding: '8px 12px',
        borderRadius: theme.radius.sm,
        textDecoration: 'none',
        color: theme.colorScheme === 'dark' ? theme.colors.dark[0] : theme.colors.gray[7],
        fontSize: theme.fontSizes.sm,
        fontWeight: 500,

        '&:hover': {
            backgroundColor: theme.colorScheme === 'dark' ? theme.colors.dark[6] : theme.colors.gray[0],
        },
    },

    linkActive: {
        '&, &:hover': {
            backgroundColor: theme.fn.variant({variant: 'light', color: theme.primaryColor}).background,
            color: theme.fn.variant({variant: 'light', color: theme.primaryColor}).color,
        },
    },
}));

interface HeaderMiddleProps {
    links: { link: string; label: string }[];
}

export function HeaderMiddle({links}: HeaderMiddleProps) {
    const [opened, {toggle}] = useDisclosure(false);
    const [active, setActive] = useState(links[0].link);
    const {classes, cx} = useStyles();
    const navigate = useNavigate();

    const items = links.map((link) => (
        <Anchor
            key={link.label}
            className={cx(classes.link, {[classes.linkActive]: active === link.link})}
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
        <Header height={56}>
            <Container className={classes.inner}>
                <Burger opened={opened} onClick={toggle} size="sm" className={classes.burger}/>
                <Group className={classes.links} spacing={5}>
                    {items}
                </Group>

                <Group spacing={0} className={classes.social} position="right" noWrap>
                    <ActionIcon size="lg">
                        <IconBrandTelegram size={18} stroke={1.5}/>
                    </ActionIcon>
                    <ActionIcon size="lg">
                        <IconBrandAmongus size={18} stroke={1.5}/>
                    </ActionIcon>
                    <ActionIcon component="a" href={contacts[0].link} size="lg">
                        <IconBrandGithub size={18} stroke={1.5}/>
                    </ActionIcon>
                </Group>
            </Container>
        </Header>
    );
}