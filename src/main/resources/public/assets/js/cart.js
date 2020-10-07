
let API_ADD_TO_CART = '/cart/add';
let API_CART_UPDATE = '/cart/update';
let API_CART_DELETE = '/cart/delete';

let METHOD_POST     = 'POST';
let METHOD_GET      = 'GET';

// Nab status cart
let notify_cart = $('#notify_cart');
function updateStatusCart(number){
    if (number < 1) {
        notify_cart.addClass("d-none");
    }else{
        notify_cart.removeClass("d-none");
    }
    notify_cart.text(number);
}

// Ajax send data
function sendData(url, method, data, callback){
    $.ajax({
        headers: {
            'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
        },
        url:     url,
        method:  method,
        data:    data,
        success: callback,
    });
}

// add card
if($('.btn_add_cart').length > 0) {
    $('.btn_add_cart').on('click', function (e){
        let id     = this.getAttribute('data-id');

        let input  = this.parentElement.querySelector('input');

        let data = {
            id: id
        };

        if (input != null){
            let value = input.value>>0;
            let max   = input.getAttribute('max')>>0;
            if (value < 1 || value > max){
                input.value = value = 1;
            }
            data.quantity = value;
        }

        let callback = function(data, textStatus, xhr){
            updateStatusCart(data.number);

            if (data.status === 200) {
                Toasts.push(data.message, 'bg-success text-white');
            } else {
                Toasts.push(data.message, 'bg-danger text-white');
            }
        }

        sendData(API_ADD_TO_CART, METHOD_POST, data, callback);
    });
}

// cart manager
let cart_item = function (element){
    this.el = element;
    this.init();
}

cart_item.prototype.init = function (){
    $(this.el.querySelector('.btn_plus')).on('click',(e) => {
        let data = {
            id: this.el.getAttribute('data-id'),
            type: 'plus'
        }
        sendData(API_CART_UPDATE, METHOD_POST, data, this.callback_update.bind(this));
    });

    $(this.el.querySelector('.btn_minus')).on('click',(e) => {
        let data = {
            id: this.el.getAttribute('data-id'),
            type: 'minus'
        }
        sendData(API_CART_UPDATE, METHOD_POST, data, this.callback_update.bind(this));
    });

    $(this.el.querySelector('.btn-delete_item')).on('click',(e) => {
        let data = {
            id: this.el.getAttribute('data-id')
        }
        sendData(API_CART_DELETE, METHOD_POST, data, this.callback_delete);
        this.el.remove();
    });
}

cart_item.prototype.callback_update = function(data, textStatus, xhr){
    this.updateQuantity(data.number);
}

cart_item.prototype.callback_delete = function(data, textStatus, xhr){
    updateStatusCart(data.number);
}

// Update quantity
cart_item.prototype.updateQuantity = function (number){
    this.el.querySelector('.quantity').textContent = number;
}

if($('.cart-item').length > 0) {
    $('.cart-item').each(function (i, element){
        new cart_item(element);
    });
}
