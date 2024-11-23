const uploadPhoto = async () => {
	const formData = new FormData();
	formData.append('data', fileupload.files[0]);
	
	// await keyword pauses function's execution until fetch operation is complete
	await fetch('http://localhost:8080/photoz', {
		method: 'POST',
		body: formData
	}).then(result => result.text()).then(text => alert(text));
}

document.getElementById('upload-button').addEventListener('click', () => {
	uploadPhoto();
})