import './App.css';
import React, {useEffect, useState} from "react";

function App() {
    const [inputText, setInputText] = useState('');
    const [message, setMessage] = useState('');
    const [selectedOption, setSelectedOption] = useState('');
    const [isLoading, setLoading] = useState(false);

    const handleSubmit = (event) => {
        event.preventDefault();
        setLoading(true);

        fetch('http://localhost:8080/api/generateArticle', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ projectDescription: inputText, option: selectedOption }),
        }) // Replace with the URL of your Spring Boot endpoint
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then((data) => {
                setMessage(data.article);
            })
            .catch((error) => {
                console.error(error);
            })
            .finally(() => {
                setLoading(false);
            });
    }

    useEffect(() => {
        // Initialize message when the component first mounts
        setMessage('<p>No message received yet.</p>');
    }, []);
  return (
      <div className = "App">
          <h1>Project article generator</h1>
          <form onSubmit={handleSubmit} >
                 <textarea
                     rows="30"  // You can adjust the number of rows as needed
                     cols="105" // You can adjust the number of columns as needed
                     placeholder="Enter project description"
                     value={inputText}
                     onChange={(e) => setInputText(e.target.value)}
                 />
              <div style={{ display: 'flex', flexDirection: 'row', justifyContent: 'center'}}>
                  <label>
                      <input
                          type="radio"
                          value="twitter"
                          checked={selectedOption === 'twitter'}
                          onChange={() => setSelectedOption('twitter')}
                      />
                      Twitter Style
                  </label>
                  <br />
                  <label>
                      <input
                          type="radio"
                          value="abstract"
                          checked={selectedOption === 'abstract'}
                          onChange={() => setSelectedOption('abstract')}
                      />
                      Abstract Description
                  </label>
                  <br />
                  <label>
                      <input
                          type="radio"
                          value="longarticle"
                          checked={selectedOption === 'longArticle'}
                          onChange={() => setSelectedOption('longArticle')}
                      />
                      Long Article
                  </label>
              </div>
              <button type="submit" disabled={isLoading}>Generate article</button>
          </form>
          {isLoading ? (
              <div>Loading... This might take a few seconds</div>
          ) : (
              <iframe
                  title="HTML Content"
                  srcDoc={message} // Set the iframe's srcDoc with the HTML content
                  style={{ border: '1px solid #ccc', width: '800px', height: '500px', margin: '16px' }}
              />
          )}

      </div>
  );
}

export default App;
