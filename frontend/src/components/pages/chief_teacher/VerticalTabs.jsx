// VerticalTabs.jsx
import * as React from 'react';
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Box from '@mui/material/Box';
import CreateUser from "./CreateUser";
import DeleteUser from "./DeleteUser";
import CreateSubject from "./CreateSubject";
import DeleteSubject from "./DeleteSubject";
import AddTeacher from "./AddTeacher";
import DeleteTeacher from "./DeleteTeacher";

function a11yProps(index) {
    return {
        id: `vertical-tab-${index}`,
        'aria-controls': `vertical-tabpanel-${index}`,
    };
}

export default function VerticalTabs({ setCurrentForm }) {
    const [value, setValue] = React.useState(0); // Початкова форма - перший таб

    const handleChange = (event, newValue) => {
        setValue(newValue);
        setCurrentForm(newValue); // Передача індексу вибраної вкладки
    };

    const renderTabPanel = (index) => {
        switch (index) {
            case 0:
                return <CreateUser />;
            case 1:
                return <DeleteUser />;
            case 2:
                return <CreateSubject />;
            case 3:
                return <DeleteSubject />;
            case 4:
                return <AddTeacher />;
            case 5:
                return <DeleteTeacher />;
            default:
                return null;
        }
    };

    return (
        <Box
            sx={{ display: 'flex', height: 400 }}
        >
            <Tabs
                orientation="vertical"
                variant="scrollable"
                value={value}
                onChange={handleChange}
                aria-label="Vertical tabs example"
                sx={{ borderRight: 1, borderColor: 'divider' }}
            >
                <Tab label="Create User" {...a11yProps(0)} />
                <Tab label="Delete User" {...a11yProps(1)} />
                <Tab label="Create Subject" {...a11yProps(2)} />
                <Tab label="Delete Subject" {...a11yProps(3)} />
                <Tab label="Add Teacher" {...a11yProps(4)} />
                <Tab label="Delete Teacher" {...a11yProps(5)} />
            </Tabs>
            <Box ml={2}>
                {renderTabPanel(value)}
            </Box>
        </Box>
    );
}

VerticalTabs.propTypes = {
    setCurrentForm: PropTypes.func.isRequired,
};
