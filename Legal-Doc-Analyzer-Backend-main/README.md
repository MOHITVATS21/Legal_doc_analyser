# AI-Powered Legal Document Analyzer

## 📌 Overview
The **AI-Powered Legal Document Analyzer** is a tool designed to automate legal document analysis using Natural Language Processing (NLP). It helps in identifying high-risk clauses, ambiguous terms, compliance issues, and provides mitigation recommendations to assist legal professionals in contract review and risk mitigation.

## 🚀 Features
- **Clause Risk Analysis**: Detects potentially high-risk contractual terms.
- **Ambiguity Detection**: Highlights vague or open-ended language.
- **Regulatory Compliance Check**: Identifies potential legal violations.
- **Mitigation Suggestions**: Provides recommendations for reducing risk.
- **Lightweight & Fast**: Uses **H2 Database** for temporary storage, requiring no external database setup.

## 🛠️ Technologies Used
- **Java Spring Boot** (Backend API)
- **H2 Database** (In-memory database for temporary data storage)
- **Hugging Face Transformers** (NLP-based document processing)
- **OpenAI GPT APIs** (For document analysis and recommendations)

## 📦 Installation & Setup
### Prerequisites
- Java 17+
- Maven
- Git

### Clone the Repository
```sh
 git clone https://github.com/warcryy/Legal-Doc-Analyzer-Backend.git
 cd Legal-Doc-Analyzer-Bakend
```

### Configure Hugging Face API Token
1. Go to [Hugging Face](https://huggingface.co/settings/tokens) and generate an access token.
2. Add the token to your `application.properties` file:
   ```properties
   openai.api.key=your_huggingface_api_key
   ```

### Build and Run the Application
```sh
mvn clean install
mvn spring-boot:run
```

## 🛠 Usage
- Upload a legal document via API or UI.
- The system will analyze clauses for risk, ambiguity, and compliance issues.
- Review the recommendations generated.

## 🤝 Contributing
Pull requests are welcome! Feel free to contribute and improve this project.

## 📜 License
This project is licensed under the MIT License.

## 📞 Contact
For any issues or inquiries, reach out via GitHub issues or email.
