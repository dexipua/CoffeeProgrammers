import * as React from 'react';
import Box from '@mui/material/Box';
import Tab from '@mui/material/Tab';
import TabContext from '@mui/lab/TabContext';
import TabList from '@mui/lab/TabList';
import TabPanel from '@mui/lab/TabPanel';
import SchoolNews from "./news/SchoolNews";
import UserNews from "./news/UserNews";

export default function News() {
    const [value, setValue] = React.useState('1');

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    return (
        <Box
            display="flex"
            flexDirection="column"
            justifyContent="center"
            alignItems="center" mt="80px" sx={{width: '100%', typography: 'body1'}}
        >
            <TabContext value={value}>
                <Box sx={{borderBottom: 1, borderColor: 'divider'}}>
                    <TabList onChange={handleChange} aria-label="lab API tabs example">
                        <Tab label="School news" value="1"/>
                        <Tab label="My news" value="2"/>
                    </TabList>
                </Box>
                <TabPanel value="1">
                    <SchoolNews/>
                </TabPanel>
                <TabPanel value="2">
                    <UserNews/>
                </TabPanel>
            </TabContext>
        </Box>
    );
}