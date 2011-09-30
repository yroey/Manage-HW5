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
		  $('#course_msg').html('You were successfully registerd to this course').show(300);
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
        $('#registered_course_' + course_id).remove();
        $('#registered').hide(0);
        $('#not_registered').show(0);
      }, 'json');
  }

  function showTimeTable() {
	  $.get('time_table.jsp', function(data) {
		  $('#time_table').html(data);
      $('.main_pane').hide(0);
      $('#time_table').show(0);
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
	      $.get('CourseSearch', {'id': params['id']}, function(data){
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
  }

  var current_hash = window.location.hash;

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
	 var template = $('#course_template');
	 $('#courses').html('').append(template);
	 for (i in courses) {
	   var course = courses[i];
	   var new_course = template.clone();
	  var url = new_course.find('a').attr('href').replace('{{ID}}', course['id']);
	  new_course.find('a').attr('href', url);
	  var onclick = new_course.find('a').attr('onclick').replace('{{ID}}', course['id']);
	  new_course.find('a').attr('onclick', onclick);
	   new_course.find('.course_name').html(course['name']).end()
	   .data('id', course['id'])
	   .removeAttr('id').appendTo('#courses').show(0);
	 }
  });
}

  function set_course_search_url() {
	  var name = $('#course_name').val();
	  var available = $('#available').prop('checked') ? '1': '0';
	  setUrl('course_search?name=' + name + '&available=' + available);
  }