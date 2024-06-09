import AccountService from "../../../services/AccountService";
import {useEffect, useState} from "react";
import ButtonAppBar from "../../layouts/ButtonAppBar";
import AccountContainer from "../../common/user/AccountContainer";
import SubjectWithTeacherList from "../../common/subject/SubjectWithTeacherList";
import {Link} from "react-router-dom";

const Account = () => {
    const [account, setAccount] = useState(null); // Початкове значення має бути null або пустий об'єкт для правильного завантаження
    const [loading, setLoading] = useState(true); // Додано стан завантаження

    useEffect(() => {
        const token = localStorage.getItem("jwtToken");

        const fetchAccount = async () => {
            try {
                const response = await AccountService.getAccount(token);
                setAccount(response);
            } catch (error) {
                console.error('Error fetching account:', error);
            } finally {
                setLoading(false); // Встановіть стан завантаження в false після завершення завантаження
            }
        };

        fetchAccount().then(() => {
        });
    }, []);

    if (loading) {
        return <div>Loading...</div>; // Або інший індикатор завантаження
    }

    const role = localStorage.getItem('role')
    console.log(role)
    return (
        <div>
            <ButtonAppBar/>
            <div className="card">
                <AccountContainer user={account}/>
            </div>
            <div className="card">
                {role === "STUDENT" ? (
                    <Link to={`/marks/getAllByStudentId/${account.id}`}><div>My marks</div></Link>
                ) : role === "TEACHER" ? (
                    <div></div>
                ) : role === "CHIEF_TEACHER" ? (
                    <Link to={"/admin"}><div>Admin panel</div></Link>
                ) : (
                    <div></div>
                )}
            </div>
            <h3>Subjects</h3>
            <div>
                <SubjectWithTeacherList
                    subjects={account.subjects}/>
            </div>
        </div>
    );
}

export default Account;
