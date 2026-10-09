import { Link, NavLink } from 'react-router-dom'
import "./Header.css";

export default function Header() {
  return (
    <header className='header'>
      <div className='header-inner'>
        <Link to='/' className='header-logo'>
          Beatitudo
        </Link>

        <nav className='header-nav'>
          <NavLink to='/cafe'>CAFE</NavLink>
          <NavLink to='/community'>COMMUNITY</NavLink>
          <NavLink to='/shopping'>SHOPPING</NavLink>
        </nav>

        <div className='header-right'>
          <Link to='/login'>로그인</Link>
        </div>
      </div>
    </header>
  )
}
