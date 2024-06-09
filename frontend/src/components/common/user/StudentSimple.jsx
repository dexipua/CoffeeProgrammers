import {Link} from "react-router-dom";

const StudentSimple = ({id, name}) => {
    return (
        <div className="userData">
            <Link to={`/students/getById/${id}`}><p>Name: {name}</p></Link>
        </div>
    )
}

export default StudentSimple;