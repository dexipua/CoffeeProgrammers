import {createTheme} from '@mui/material/styles';

const Theme = createTheme({
    palette: {
        background: {
            default: '#f5f5f5',
        },
    },
    components: {
        MuiCssBaseline: {
            styleOverrides: {
                body: {
                    backgroundColor: '#f5f5f5',
                },
            },
        },
    },
});

export default Theme;