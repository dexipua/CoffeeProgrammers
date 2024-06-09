import {Link} from "react-router-dom";

const StudentSimple = ({id, name}) => {
    return (
        <div className="userData">
            <p><Link to={`/students/getById/${id}`}>{name}</Link></p>
        </div>
    )
}

export default StudentSimple;