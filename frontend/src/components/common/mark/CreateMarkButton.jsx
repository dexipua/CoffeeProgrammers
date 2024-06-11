import React, {useState} from 'react';
import AddIcon from '@mui/icons-material/Add';
import CreateMarkDialog from './CreateMarkDialog';
import IconButton from "@mui/material/IconButton"; // Шлях до вашого компонента діалогу створення оцінки

function CreateMarkButton({studentId, subjectId, onMarkCreate}) {
    const [dialogOpen, setDialogOpen] = useState(false);

    const handleOpenDialog = () => {
        setDialogOpen(true);
    };

    const handleCloseDialog = (studentId, createdMark) => {
        setDialogOpen(false);
        onMarkCreate(studentId, createdMark)
    };

    return (
        <>
            <IconButton
                variant="contained"
                onMouseEnter={(e) => e.currentTarget.style.color = 'green'}
                onMouseLeave={(e) => e.currentTarget.style.color = ''}
                onClick={handleOpenDialog}
                sx={{ minWidth: 'unset', width: 'auto', p: 0 }} // Забезпечуємо мінімальну ширину і відступи
            >
                <AddIcon sx={{ fontSize: 24 }} /> {/* Задаємо розмір іконки */}
            </IconButton>
            <CreateMarkDialog
                subjectId={subjectId}
                studentId={studentId}
                open={dialogOpen}
                onClose={handleCloseDialog}/>
        </>
    );
}

export default CreateMarkButton;
