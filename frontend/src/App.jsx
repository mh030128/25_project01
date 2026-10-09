import { BrowserRouter, Routes, Route } from "react-router-dom";
import Header from "./components/Header";
import LoginPage from "./pages/login/LoginPage";
import SignUpPage from "./pages/signup/SignUpPage";
import HomePage from "./pages/HomePage";
import CafePage from "./pages/cafe/CafePage";
import CommunityPage from "./pages/community/CommunityPage";
import ShoppingPage from "./pages/shopping/ShoppingPage";
import MyPage from "./pages/users/MyPage";


function App() {
  return (
    <BrowserRouter>
    <div>
      <Header />
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/signup" element={<SignUpPage />} />
        <Route path="/cafe" element={<CafePage />} />
        <Route path="/community" element={<CommunityPage />} />
        <Route path="/shopping" element={<ShoppingPage />} />
        <Route path="/mypage" element={<MyPage />} />
      </Routes>
    </div>
    </BrowserRouter>
  );
}

export default App;