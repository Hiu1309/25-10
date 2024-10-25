'use strict';
function getElementsHandler() {
    let getElements = {
        getCartItems: () => document.querySelectorAll(".block-product"), 
        getQuantityInputs: () => document.querySelectorAll(".quantity-cart"), 
        getTotalPrice: () => document.querySelector(".total-price"),  
        getRemoveButtons: () => document.querySelectorAll(".fa-trash"),  
        getSelectAllCheckbox: () => document.querySelector('#selection-item'),  
    };
    return getElements;
}

function updateCartTotal(elementsObj) {
    const cartItems = elementsObj.getCartItems();
    let total = 0;

    cartItems.forEach(item => {
        const checkbox = item.querySelector('input[type="checkbox"]'); 
        if (checkbox.checked) {  
            const price = parseFloat(item.querySelector(".price").innerText);  
            const quantity = parseInt(item.querySelector(".quantity-cart").value); 
            total += price * quantity;  
        }
    });

    elementsObj.getTotalPrice().innerText = `${total.toFixed(2)} đ`;
}

function handleQuantityChange(elementsObj) {
    const quantityInputs = elementsObj.getQuantityInputs();
    quantityInputs.forEach(input => {
        input.addEventListener("change", () => {
            updateCartTotal(elementsObj); 
        });
    });
}

function handleCheckboxChange(elementsObj) {
    const cartItems = elementsObj.getCartItems();
    cartItems.forEach(item => {
        const checkbox = item.querySelector('input[type="checkbox"]');
        checkbox.addEventListener("change", () => {
            updateCartTotal(elementsObj); 
        });
    });
}
function handleSelectAllCheckbox(elementsObj) {
    const selectAllCheckbox = elementsObj.getSelectAllCheckbox();
    const cartItems = elementsObj.getCartItems();

    selectAllCheckbox.addEventListener('change', () => {
        const isChecked = selectAllCheckbox.checked;

        cartItems.forEach(item => {
            const checkbox = item.querySelector('input[type="checkbox"]');
            checkbox.checked = isChecked;
        });

        updateCartTotal(elementsObj);
    });
}

function handleRemoveItem(elementsObj) {
    const removeButtons = elementsObj.getRemoveButtons();
    removeButtons.forEach(button => {
        button.addEventListener("click", (event) => {
            event.target.closest(".block-product").remove(); 
            updateCartTotal(elementsObj); 
        });
    });
}
document.addEventListener("DOMContentLoaded", function () {
    let elementsObj = getElementsHandler();
    updateCartTotal(elementsObj); 
    handleQuantityChange(elementsObj);  
    handleCheckboxChange(elementsObj); 
    handleSelectAllCheckbox(elementsObj);  
    handleRemoveItem(elementsObj);  
});
function goToCart() {
    window.location.href = "cart.html";  
}


