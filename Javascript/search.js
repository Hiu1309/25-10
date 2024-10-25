function redirectSearch(event) {
  event.preventDefault(); // Ngăn không cho form gửi dữ liệu mặc định
  const searchQuery = document.getElementById("searchInput").value.trim();
  if (searchQuery) {
    // Chuyển hướng tới list.html kèm từ khóa tìm kiếm
    window.location.href = `search.html?search=${encodeURIComponent(
      searchQuery
    )}`;
  }
}
// Hàm lấy giá trị query string từ URL
function getQueryParam(param) {
  const urlParams = new URLSearchParams(window.location.search);
  return urlParams.get(param);
}

function searchBooks() {
  const searchInput = getQueryParam("search");
  const resultsContainer = document.getElementById("resultsContainer");

  // Lấy tất cả các phần tử sách từ HTML
  const bookItems = Array.from(document.getElementsByClassName("book-item"));

  // Ẩn tất cả các sách ban đầu
  bookItems.forEach((book) => {
    book.style.display = "none";
  });

  // Lọc sách dựa trên từ khóa tìm kiếm và sắp xếp theo độ chính xác (relevance)
  const filteredBooks = bookItems
    .filter((book) => book.innerText.toLowerCase().includes(searchInput))
    .sort((a, b) => {
      const relevanceA = parseInt(a.getAttribute("data-relevance"));
      const relevanceB = parseInt(b.getAttribute("data-relevance"));
      return relevanceB - relevanceA;
    });

  // Hiển thị sách đã lọc
  if (filteredBooks.length > 0) {
    filteredBooks.forEach((book) => {
      book.style.display = "block";
    });
  } else {
    resultsContainer.innerHTML = "<p>Không tìm thấy sách nào phù hợp.</p>";
  }
}

function handleEnter(event) {
  if (event.keyCode === 13) {
    // Kiểm tra nếu phím Enter (mã là 13) được nhấn
    searchBooks();
  }
}

window.onload = function () {
  searchBooks();
};
