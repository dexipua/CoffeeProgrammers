import React, {useEffect} from 'react';
import {useParams} from "react-router-dom";
import MarkService from '../../../services/MarkService'
import '../../../assets/styles/StudentMarks.css';
import ButtonAppBar from "../../layouts/ButtonAppBar";

const StudentMarks = () => {
    const {id} = useParams();
    const [marks, setMarks] = React.useState([{
        subjectResponseSimple: {
            id: -1,
            name: " "
        },
        marks: [
            {
                id: -1,
                value: -1
            }]
    }]);

    useEffect(() => {
        const token = localStorage.getItem('jwtToken');
        const fetchMarkById = async () => {
            try {
                const response = await MarkService.getByStudentId(id, token);
                setMarks(response);
            } catch (error) {
                console.error('Error fetching mark data:', error);
            }
        };

        fetchMarkById()
            .then(() => {
            console.log('Mark data fetched successfully');
        })
    }, [id]);

    return (
        <div>
            <ButtonAppBar />
            <h2>Student Marks</h2>
            <table className="student-marks-table">
                <thead>
                <tr>
                    <th>Subject</th>
                    <th>Marks</th>
                </tr>
                </thead>
                <tbody>
                {marks.map((subjectData, index) => (
                    <tr key={index}>
                        <td>{subjectData.subjectResponseSimple.name}</td>
                        <td>
                            {subjectData.marks.map((mark, index) => (
                                <span key={index}>{mark.value}{index !== subjectData.marks.length - 1 ? ', ' : ''}</span>
                            ))}
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default StudentMarks;