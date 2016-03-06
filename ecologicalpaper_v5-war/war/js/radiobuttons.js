    function radioButton(radio) {
        var id = radio.name.substring(radio.name.lastIndexOf(':'));
        var el = radio.form.elements;
        for (var i = 0; i < el.length; i++) {
            //if (el[i].name.substring(el[i].name.lastIndexOf(':')) == id) {
            if (el[i].type == 'radio') {
                el[i].checked = false;
            }
        }
        radio.checked = true;
    }