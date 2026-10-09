import { Link, NavLink, useLocation, useNavigate } from 'react-router-dom'
import "./Header.css";
import { useState, useRef, useEffect } from 'react';

export default function Header() {
  useLocation();

  const navigate = useNavigate();
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const menuRef = useRef(null);

  useEffect(() => {
    if (!isMenuOpen) return;

    const handleClickOutside = (e) => {
      if (menuRef.current && !menuRef.current.contains(e.target)) {
        setIsMenuOpen(false);
      }
    }

    document.addEventListener('mousedown', handleClickOutside); // 감시장치 달기


    return () => {
      document.removeEventListener('mousedown', handleClickOutside);  // 감시장치 닫기
    }
  }, [isMenuOpen]);    // 메뉴가 열리고 닫힐 때마다 실행.


  const token = localStorage.getItem('accessToken');
  const userName = localStorage.getItem('userName');

  const handleLogout = () => {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('userNo');
    localStorage.removeItem('userId');
    localStorage.removeItem('userName');

    setIsMenuOpen(false);
    navigate('/');

  }

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
          {token ? (
            <div className='user-menu' ref={menuRef}>
              <button type='button' className='user-menu-button' onClick={() => {setIsMenuOpen(!isMenuOpen)}}> {userName}님 ▾ </button>
              {isMenuOpen && (
                <div className='user-dropdown'>
                  <Link to='/myPage' onClick={() => setIsMenuOpen(false)}>마이페이지</Link>
                  <button type='button' onClick={handleLogout}>로그아웃</button>
                </div>
              )}
            </div>
          ) : (<Link to='/login'>로그인</Link>) }
        </div>
      </div>
    </header>
  )
}
