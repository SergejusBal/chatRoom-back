package ChatNewPage.Chat.Repositories;

import ChatNewPage.Chat.Models.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MessageRepository {
    @Autowired
    private UserRepository userRepository;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;


    public String registerMessage(ChatMessage chatMessage){

        if(chatMessage.getName() == null ||
           chatMessage.getMessage() == null ||
           chatMessage.getSesionID() == null ||
           chatMessage.getLocalDateTime() == null) return "Invalid data";

        String sql = "INSERT INTO messages (user_name, session_id, message, datetime)\n" +
                "VALUES (?,?,?,?);";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, chatMessage.getName());
            preparedStatement.setString(2, chatMessage.getSesionID());
            preparedStatement.setString(3, chatMessage.getMessage());
            preparedStatement.setString(4, chatMessage.getLocalDateTime().toString());

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

        }catch (SQLException e) {
            System.out.println(e.getMessage());

            return "Database connection failed";
        }
        return "Message was successfully added";
    }

    public List<ChatMessage> getLast100Messages(){

        List<ChatMessage> chatMessages = new ArrayList<>();

        String sql = "SELECT * FROM event_shop.comments ORDER BY id DESC LIMIT 100";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet =  preparedStatement.executeQuery();

            while(resultSet.next()) {

                ChatMessage chatMessage = new ChatMessage();

                chatMessage.setName(resultSet.getString("user_name"));
                chatMessage.setMessage(resultSet.getString("message"));

                LocalDateTime date = formatDateTime(resultSet.getString("datetime"));
                chatMessage.setLocalDateTime(date);

                chatMessages.add(chatMessage);
            }

        }catch (SQLException e) {

            System.out.println(e.getMessage());
            return new ArrayList<>();
        }

        return chatMessages;

    }

        private LocalDateTime formatDateTime(String dateTime){
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime rentalDate = null;
            try {
                rentalDate = LocalDateTime.parse(dateTime, dateTimeFormatter);
            }catch(DateTimeParseException | NullPointerException e) {
                rentalDate = LocalDateTime.parse("1900-01-01 00:00:00", dateTimeFormatter);
            }
            return rentalDate;
        }


    }
