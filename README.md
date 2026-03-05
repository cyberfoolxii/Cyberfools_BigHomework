# English-learning Application (Dictionary) 📚

Ứng dụng từ điển Anh-Việt dùng cho việc học tiếng Anh với giao diện đồ họa JavaFX, tích hợp nhiều tính năng học tập và giải trí.

## 📋 Mô tả

Đây là một ứng dụng từ điển đa chức năng được xây dựng bằng Java và JavaFX, cung cấp khả năng tra cứu từ vựng, dịch thuật và các trò chơi học tập giúp người dùng cải thiện vốn từ vựng tiếng Anh.

## ✨ Tính năng chính

### 📖 Quản lý từ điển
- **Từ điển ngoại tuyến**: Tra cứu từ vựng từ cơ sở dữ liệu local
- **Từ điển trực tuyến**: Tích hợp API để tra cứu định nghĩa từ online
- **Thêm/Sửa/Xóa từ**: Quản lý từ vựng cá nhân
- **Tìm kiếm nhanh**: Tìm kiếm từ với gợi ý tự động

### 🔊 Phát âm
- Phát âm chuẩn cho từ vựng tiếng Anh
- Hỗ trợ text-to-speech

### 🌐 Dịch thuật
- Dịch câu/đoạn văn Anh-Việt và Việt-Anh
- Hỗ trợ dịch nhiều câu cùng lúc

### 🎮 Trò chơi học tập

#### 1. Memory Card Game (Trò chơi lật thẻ)
- Tìm cặp từ tiếng Anh - nghĩa tiếng Việt
- Nhiều mức độ khó khác nhau
- Theo dõi điểm số và thời gian
- Lưu điểm cao nhất

#### 2. Multiple Choice Game (Trò chơi trắc nghiệm)
- Câu hỏi trắc nghiệm về từ vựng
- Hệ thống mạng sống
- Tính điểm và theo dõi tiến độ
- Câu hỏi ngẫu nhiên từ bộ dữ liệu

### 🎨 Giao diện
- Giao diện đẹp mắt với JavaFX
- Hỗ trợ chủ đề sáng/tối (Nord theme)
- Hiệu ứng chuyển động mượt mà
- Font chữ Quicksand tự nhiên, dễ đọc

## 🛠️ Công nghệ sử dụng

- **Ngôn ngữ**: Java 20
- **Framework UI**: JavaFX 21.0.1
- **Build Tool**: Maven
- **Thư viện**:
  - JavaFX Graphics, FXML, Media, Controls
  - Gson 2.9.0 (xử lý JSON)
  - HttpClient (API calls)

## 🚀 Hướng dẫn cài đặt

### Yêu cầu hệ thống
- Java Development Kit (JDK) 20 trở lên
- Apache Maven 3.6 trở lên
- Kết nối Internet (cho tính năng từ điển online và dịch thuật)

### Các bước cài đặt

1. **Clone repository**
```bash
git clone <repository-url>
cd Cyberfools_BigHomework-master
```

2. **Build project với Maven**
```bash
mvn clean install
```

3. **Chạy ứng dụng**
```bash
mvn javafx:run
```

Hoặc chạy trực tiếp từ IDE:
- Mở project trong IntelliJ IDEA hoặc Eclipse
- Run class `DictionaryApplication.java`

## 💡 Hướng dẫn sử dụng

### Tra cứu từ vựng
1. Mở ứng dụng
2. Nhập từ cần tra vào ô tìm kiếm
3. Chọn từ từ danh sách gợi ý
4. Xem định nghĩa, phiên âm và ví dụ
5. Nhấn nút loa để nghe phát âm

### Quản lý từ
- **Thêm từ mới**: Nhập từ và định nghĩa, sau đó lưu
- **Sửa từ**: Chọn từ cần sửa, chỉnh sửa thông tin và cập nhật
- **Xóa từ**: Chọn từ cần xóa và xác nhận

### Chơi game
1. Chọn menu Games
2. Chọn loại game (Memory Card hoặc Multiple Choice) (và chọn độ khó cho Memory Card Game)
4. Bắt đầu chơi và học từ vựng

## 🎯 Class chính

| Class | Mô tả |
|-------|-------|
| `DictionaryApplication` | Main application class, entry point |
| `Dictionary` | Quản lý dữ liệu từ điển |
| `LocalDictionaryManager` | Xử lý từ điển offline |
| `OnlineDictionaryManager` | Xử lý API tra cứu online |
| `MemoryCardGameController` | Controller cho game lật thẻ |
| `MultipleChoiceGameController` | Controller cho game trắc nghiệm |
| `SpeechManager` | Quản lý phát âm |
| `SceneManager` | Quản lý các scene của ứng dụng |
| `EnglishWord`, `VietnameseWord` | Model cho từ vựng |

## 📝 License

Project này được phát triển cho mục đích học tập.

## 👥 Team Cyberfools

Được phát triển bởi team Cyberfools 2 thành viên: Nguyễn Mạnh Cường và Nguyễn Đỗ Quang Đại như một bài tập lớn môn Lập trình Hướng đối tượng (OOP).

---

**Happy Learning! 📚✨**
