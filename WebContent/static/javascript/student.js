 function register(trigger) {
	  var container = trigger;
	  while(!$(container).hasClass('course')) {
		  container = container.parentNode;
	  }
	  var id = $(container).find('.id').html();
	  alert(id);
 }
  $(document).ready(function(){
	  loadRegisteredCourses();
	  processUrl();
  });

  function loadAllCourses() {
     $.getJSON('CourseSearch', function(data){

     $('.main_pane').hide(0);
     $('#courses_pane').show(0);
   });
  }

  function registrationResult(result) {
	  if (result['result'] == '0') {
		  $('#course_msg').html('Could not register to course. ' + (result['msg'] || '')).show(300);
	  } else {
	      var left = $('#num_students_left').html() | 0;
	      $('#num_students_left').html(left - 1);
		  $('#course_msg').html('Registration Succeeded').show(300);
		  // Add the course to the list of registered courses.
		  addRegisteredCourse(result, true);
	      $('#not_registered').hide(0);
	      $('#registered').show(0);
	  }
	  setTimeout(function(){
		  $('#course_msg').hide(300);
	  }, 5000);
  }

  function loadRegisteredCourses() {
	  $.getJSON('CourseSearch', {'registered' : 1}, function(data) {
		  var courses = data['courses'];
		  for (var i = 0; i < courses.length; ++i) {
			  addRegisteredCourse(courses[i], false);
		  }
	  });
  }

  function addRegisteredCourse(course, highlight) {
	  var course_template = $('#registered_course_template').clone();
	  var url = course_template.find('a').attr('href').replace('{{ID}}', course['id']);
	  course_template.find('a').attr('href', url);
	  var onclick = course_template.find('a').attr('onclick').replace('{{ID}}', course['id']);
	  course_template.find('a').attr('onclick', onclick);
	  course_template.find('.course_name').html(course['name']);
	  course_template.css('display', '');
	  course_template.appendTo('#registered_courses_list');
	  course_template.attr('id', 'registered_course_' + course['id']);
	  course_template.data('id', course['id']);
  }

  function register(trigger) {
	  var container = $(trigger).parents('.course');
	  if (!container) {
		  return;
	  }
	  var course_id = $(container).data('id');
	  registerById(course_id);
  }

  function registerById(course_id) {
      $.post('CourseSearch', {'course_id': course_id}, function(data){
        registrationResult(data);
      }, 'json');
  }

  function unregisterById(course_id) {
	  var sure = confirm('Are you sure?');
	  if (!sure) {
		  return;
	  }
      $.post('CourseSearch', {'course_id': course_id, 'unregister': '1'}, function(data){
    	var left = $('#num_students_left').html() | 0;
    	$('#num_students_left').html(left + 1);
        $('#registered_course_' + course_id).remove();
        $('#registered').hide(0);
        $('#not_registered').show(0);
      }, 'json');
  }

  function showTimeTable() {
	  $.get('time_table.jsp', function(data) {
		  $('#time_table').html(data);
		  loadTimeTableByFormat();
		  $('.main_pane').hide(0);
		  $('#time_table').show(0);
	  });
  }

  function loadTimeTableByFormat() {
	  var id = $('#time_table_format_selector').val();
	  $.cookies.set("time_table_format_id", id);
	  $.get('/TimeTableByFormatId', {id: id}, function(data){
		  $('#time_table_content').html(data);
	  });
  }

  function processUrl() {
	  $('#ajax-loader').show(0);
	  var hash = window.location.hash;
	  if (hash == "" || hash == "#") {
		  $('.main_pane').hide(0);
		  $('#welcome-pane').show(0);
		  return;
	  }

	  var url_parts = hash.split(['?']);
	  var action = url_parts[0].substring(1);
	  params = {};
	  if (url_parts.length > 1) {
		  string_params = url_parts[1];
		  key_values = string_params.split('&');
		  for (var i = 0; i < key_values.length; ++i) {
			  key_value = key_values[i].split('=');
			  key = key_value[0];
			  value = '';
			  if (key_value.length > 1) {
				  value = key_value[1];
			  }
			  params[key] = value;
		  }
	  }
      $('.main_pane').hide(0);
	  if (action == 'time_table') {
		  showTimeTable();
	  }
	  if (action == 'course') {
	      $.get('course.jsp', {'id': params['id']}, function(data){
	          $('#course').show(0).html(data);
	      });
	  }

	  if (action == 'show_course_search') {
		  $('#course_search').show(0);
	  }
	  if (action == 'course_search') {
		  $('#course_search').show(0);
		  course_search(params);
	  }

	  if (action == 'edit_details') {
		  show_edit_details();
	  }
  }

  var current_hash = window.location.hash;

  function show_edit_details() {
	  $.get('edit_details.jsp', function(data) {
		  $('#edit_student').html(data);
		  $('#edit_student').show(0);
	  });
  }

  function check_hash() {
      if ( window.location.hash != current_hash ) {
          current_hash = window.location.hash;
          processUrl();
      }
  }

  hashCheck = setInterval( check_hash, 50 );


  function setUrl(url) {
	  window.location.hash = url;
	  processUrl();
	  return false;
  }

function course_search(params) {
  $.getJSON('CourseSearch', params, function(data){
	 var courses = data['courses'];
	 if (courses.length == 0) {
		 $('#no_courses_msg').show(0);
		 $('#courses_search_table').hide(0);
		 return;
	 }

	 $('#no_courses_msg').hide(0);
	 $('#courses_search_table').show(0);
	 var template = $('#course_template');
	 $('#courses_search_table tr').not('.sticky').remove();
	 for (i in courses) {
	   var course = courses[i];
	   var new_course = template.clone();
	  var url = new_course.find('a').attr('href').replace('{{ID}}', course['id']);
	  new_course.find('a').attr('href', url);
	  var onclick = new_course.find('a').attr('onclick').replace('{{ID}}', course['id']);
	  new_course.find('a').attr('onclick', onclick);
	   new_course.find('.course_name').html(course['name']).end()
	   .data('id', course['id'])
	   .removeAttr('id')
	   .find('.course_credit').html(course['credit']).end()
	   .find('.course_group').html(course['group']).end().removeClass('sticky').appendTo('#courses_search_table').show(0);
	 }
  });
}

  function set_course_search_url() {
	  var q = $('#q').val();
	  var available = $('#available').prop('checked') ? '1': '0';
	  setUrl('course_search?q=' + q + '&available=' + available);
  }


  function editStudent(params, errors, container) {
	  if (errors.length == 0) {
		  $.post('EditStudent', params, function(data){
			  if (data['result'] == 0) {
				  errors.push(data['msg']);
				  showFormErrors(container, errors);
			  } else {
				  $(container).find('.saved').show(300);
				  setTimeout(function() {
					  $(container).find('.saved').hide(300);
				  }, 5000);
			  }
		  }, 'json');
	  } else {
		  showFormErrors(container, errors);
	  }
  }

  function validateEditDetails() {
	  $("#edit_details .errors").hide(0);
	  var username = $('#username').val();
	  var name = $('#name').val();
	  var phone = $('#phone').val();

	  var msgs = {'username': 'Username should contain only letter and be 5-12 charachters long',
	           	  'username_empty': 'Username is a mandatory field',
	           	  'name_empty': 'Name is a mandtaory field',
	           	  'name_too_long': 'Name should not be longer than 25 charachters',
	           	  'phone': 'Phone must be number not be longer than 25 charachters'};
	  var errors = [];
      if (!username) {
        errors.push(msgs['username_empty']);
      } else if (!username.match(/^[a-zA-Z]{5,12}$/)) {
        errors.push(msgs['username']);
      }

      if (!name) {
        errors.push(msgs['name_empty']);
      }
      if (name.length > 25) {
        errors.push(msgs['name_too_long']);
      }

      if (!phone.match(/^[0-9]{0,25}$/)) {
        errors.push(msgs['phone']);
      }
      var params = {};
      params['username'] = username;
      params['name'] = name;
      params['phone'] = phone;
      params['action'] = 'edit_details';
	  editStudent(params, errors, "#edit_details");
	  return false;
  }

  function validatePassword() {
	  $("#change_password .errors").hide(0);
	  var password = $('#password').val();
	  var repassword = $('#repassword').val();

	  var msgs = {'password': 'Password should contain only letter or number and be 5-12 charachters long',
			      'password_again': 'Passwords did not match',
			      'password_empty': 'Password is a mandatory field'};
	  var errors = [];
	  if (!password) {
	    errors.push(msgs['password_empty']);
	  } else if (!password.match(/^[a-zA-Z0-9]{5,12}$/)) {
	    errors.push(msgs['password']);
	  } else if (password != repassword) {
		  errors.push(msgs['password_again']);
	  }
      var params = {};
      params['password'] = password;
      params['action'] = 'change_password';
	  editStudent(params, errors, "#change_password");

	  return false;
  }

  function showFormErrors(container, errors) {
	  $(container).find('.errors li').remove();
	  for (i in errors) {
		  var li = $('<li></li>').html(errors[i]);
		  $(container + ' .errors').append(li);
	  }
	  $(container + " .errors").show(0);
  }