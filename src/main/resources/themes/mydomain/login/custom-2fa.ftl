<#import "template.ftl" as layout>
<@layout.registrationLayout displayInfo=true; section>
	<#if section = "header">
		${msg("smsAuthTitle",realm.displayName)}
	<#elseif section = "form">
			<#--  <div class="container">  -->
				<form action="${url.loginAction}" method="post" id="kc-sms-code-login-form" class="${properties.kcFormClass!}">
					<div class="${properties.kcFormGroupClass!}">
						<div class="${properties.kcInputWrapperClass!} " style='color: black'>
							<label for="name">Code:</label>
							<div class='inputs'>
							<input type="text" id="code1" class='auto-tab ${properties.kcInputClass!}' maxlength="1"/>
							<input type="text" id="code2" class='auto-tab ${properties.kcInputClass!}' maxlength="1"/>
							<input type="text" id="code3" class='auto-tab ${properties.kcInputClass!}' maxlength="1"/>
							<input type="text" id="code4" class='auto-tab ${properties.kcInputClass!}' maxlength="1"/>
							<input type="text" id="code5" class='auto-tab ${properties.kcInputClass!}' maxlength="1"/>
							<input type="text" id="code6" class='auto-tab ${properties.kcInputClass!}' maxlength="1"/>
							</div>
						</div>
        			<div id="kc-form-buttons" class="${properties.kcFormButtonsClass!}">
					<input class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} ${properties.kcButtonBlockClass!} ${properties.kcButtonLargeClass!}" type="submit" />
					</div>
				</div>
				</form>
			<#--  </div>  -->
			<script>
				const form = document.getElementById('kc-sms-code-login-form');
        		let inputs = document.getElementsByClassName('auto-tab')
        		console.log(inputs);
        		Array.from(inputs).forEach(function(input){
  					input.addEventListener("keyup", function(event) {
					if (input.value.length == 1) {
					input.nextElementSibling.focus()
					}
					if(event.keyCode == 8){
					input.previousElementSibling.focus()
					}
  					});
				})


				let field1 = form.elements['code1'];
				let field2 = form.elements['code2'];
				let field3 = form.elements['code3'];
				let field4 = form.elements['code4'];
				let field5 = form.elements['code5'];
				let field6 = form.elements['code6'];

				let code = document.createElement("input");
				code.id = 'code';
				code.name='code';
				code.type='hidden';

				form.addEventListener('submit', (event) => {

				let field1Value = field1.value;
				let field2Value = field2.value;
				let field3Value = field3.value;
				let field4Value = field4.value;
				let field5Value = field5.value;
				let field6Value = field6.value;

				const codeValue = '' + field1Value + field2Value + field3Value + field4Value + field5Value + field6Value;
				code.value = codeValue;
				form.appendChild(code);
				form.submit();
				});
			</script>
	</#if>
</@layout.registrationLayout>