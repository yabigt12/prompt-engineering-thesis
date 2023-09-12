import {fireEvent, render, screen, waitFor} from '@testing-library/react';
import App from './App';

// Mock the fetch function
global.fetch = jest.fn();

test('renders the App component with required elements', () => {
  render(<App />);
  // Check if the component renders without errors
  expect(screen.getByText('Project article generator')).toBeInTheDocument();

  // Check if the textarea is present
  expect(screen.getByPlaceholderText('Enter project description')).toBeInTheDocument();

  // Check if the radio buttons are present
  expect(screen.getByText('Twitor Style')).toBeInTheDocument();
  expect(screen.getByText('Abstract Description')).toBeInTheDocument();
  expect(screen.getByText('Long Article')).toBeInTheDocument();

  // Check if the "Generate article" button is present
  expect(screen.getByText('Generate article')).toBeInTheDocument();

  // Check if the iframe is present
  expect(screen.getByTitle('HTML Content')).toBeInTheDocument();
});

test('sends text to the backend and displays received text in iframe', async () => {
  // Mock the fetch response
  const mockResponse = { article: '<p>Mocked article content.</p>' };
  fetch.mockResolvedValueOnce({
    ok: true,
    json: async () => mockResponse,
  });

  render(<App />);

  // Fill in the textarea
  const textarea = screen.getByPlaceholderText('Enter project description');
  fireEvent.change(textarea, { target: { value: 'This is a test description.' } });

  // Click the "Generate article" button
  const generateButton = screen.getByText('Generate article');
  fireEvent.click(generateButton);

  // Wait for the fetch request and content update
  await waitFor(() => {
    expect(fetch).toHaveBeenCalledTimes(1);
    expect(fetch).toHaveBeenCalledWith(
        'http://localhost:8080/api/generateArticle',
        expect.objectContaining({
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({projectDescription: 'This is a test description.'}),
        })
    );
  });

    // Wait for the iframe content to update
  await waitFor(() => {
    const iframe = screen.getByTitle('HTML Content');
    expect(iframe.srcdoc).toBe('<p>Mocked article content.</p>');
  });

});
