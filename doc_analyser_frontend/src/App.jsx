import React, { useState } from "react";

const App = () => {
  const [file, setFile] = useState(null);
  const [docId, setDocId] = useState("");
  const [uploadResponse, setUploadResponse] = useState(null);
  const [report, setReport] = useState(null);
  const [recommendations, setRecommendations] = useState(null);
  const [error, setError] = useState(null);

  const handleFileChange = (event) => {
    setFile(event.target.files[0]);
  };

  const handleUpload = async () => {
    if (!file) {
      alert("Please select a file to upload.");
      return;
    }
    const formData = new FormData();
    formData.append("file", file);
    try {
      const response = await fetch("http://localhost:8080/api/legal-documents/upload", {
        method: "POST",
        body: formData,
      });
      const data = await response.json();
      if (response.ok) {
        setUploadResponse(data);
        setDocId(data.docId || "");
      } else {
        setError(data.error);
      }
    } catch (err) {
      setError("Failed to upload document.");
    }
  };

  const fetchReport = async () => {
    if (!docId) {
      alert("Please enter a document ID.");
      return;
    }
    try {
      const response = await fetch(`http://localhost:8080/api/legal-documents/report/${docId}`);
      const data = await response.json();
      if (response.ok) {
        setReport(data);
      } else {
        setError(data.error);
      }
    } catch (err) {
      setError("Failed to fetch report.");
    }
  };

  const fetchRecommendations = async () => {
    if (!docId) {
      alert("Please enter a document ID.");
      return;
    }
    try {
      const response = await fetch(`http://localhost:8080/api/legal-documents/recommendations/${docId}`);
      const data = await response.json();
      if (response.ok) {
        setRecommendations(data);
      } else {
        setError(data.error);
      }
    } catch (err) {
      setError("Failed to fetch recommendations.");
    }
  };

  return (
    <div>
      <h1>Legal Document Analyzer</h1>
      <input type="file" onChange={handleFileChange} />
      <button onClick={handleUpload}>Upload Document</button>
      {uploadResponse && <p>Document uploaded successfully. ID: {uploadResponse.docId}</p>}
      
      <input
        type="text"
        placeholder="Enter Document ID"
        value={docId}
        onChange={(e) => setDocId(e.target.value)}
      />
      <button onClick={fetchReport}>Get Report</button>
      <button onClick={fetchRecommendations}>Get Recommendations</button>

      {report && <pre>{JSON.stringify(report, null, 2)}</pre>}
      {recommendations && <pre>{JSON.stringify(recommendations, null, 2)}</pre>}
      {error && <p style={{ color: "red" }}>{error}</p>}
    </div>
  );
};

export default App;
