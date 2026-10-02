function HomePage() {
  const userName = localStorage.getItem("userName");

  return (
    <div style={{ padding: "40px" }}>
      <h1>홈</h1>
      {userName ? <p>{userName}님, 환영합니다!</p> : <p>로그인이 필요합니다.</p>}
    </div>
  );
}

export default HomePage;