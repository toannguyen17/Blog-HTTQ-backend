
let Toast_custom = function (){
}

Toast_custom.prototype.push = function (notification, _class = ''){
    let toast = document.createElement('div');
    toast.setAttribute('class', 'toast ' + _class);
    toast.setAttribute('role', 'alert');
    toast.setAttribute('aria-live', 'assertive');
    toast.setAttribute('aria-atomic', 'true');
    toast.innerHTML = '<button type="button" class="mr-1 close" data-dismiss="toast" aria-label="Close">\n' +
        '<span aria-hidden="true">&times;</span></button>\n' +
        '<div class="toast-body">' + notification + '</div>';

    $('#toast_push').append(toast);

    toast = $(toast);

    toast.toast({
        animation: true,
        autohide: true,
        delay: 3000
    }).toast('show');

    toast.on('hidden.bs.toast', function () {
        toast.remove();
    })
}

const Toasts = new Toast_custom();