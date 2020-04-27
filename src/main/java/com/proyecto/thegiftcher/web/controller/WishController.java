package com.proyecto.thegiftcher.web.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.proyecto.thegiftcher.domain.Wish;
import com.proyecto.thegiftcher.service.IWishService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(
		value = "/wishes"
)
public class WishController {

	private final IWishService wishService;

	public WishController(IWishService wishService) {
		this.wishService = wishService;
	}
	
	@GetMapping(path = "/all_wishes")
	public ResponseEntity<List<Wish>> getAllWishes() {
		List<Wish> allWishes = wishService.getAll();
		return new ResponseEntity<>(allWishes, HttpStatus.OK);
	}
	
	@GetMapping(path = "/")
	public ResponseEntity<List<Wish>> getWishes(HttpServletRequest request){
		List<Wish> wishes = wishService.getAll(request);
		return new ResponseEntity<>(wishes, HttpStatus.OK);
	}

	@GetMapping(path = "/userId/{userId}")
	public ResponseEntity<List<Wish>> getWishes(@PathVariable long userId){
		List<Wish> wishes = wishService.getUserWishes(userId);
		return new ResponseEntity<>(wishes, HttpStatus.OK);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Wish> getOne(@PathVariable long id, HttpServletRequest request) throws Exception {
		Wish wish = wishService.get(id, request);

		return new ResponseEntity<>(wish, HttpStatus.OK);
	}

	@GetMapping(path = "/categories/{categoryId}")
	public ResponseEntity<List<Wish>> getWishByCategory(@PathVariable long categoryId) throws Exception {
		List<Wish> wish = wishService.getWishByCategoryId(categoryId);

		return new ResponseEntity<>(wish, HttpStatus.OK);
	}
	
	@PostMapping(path = "/")
	public Map<String, String> addwish(@RequestBody Wish wish, HttpServletRequest request) {
		wishService.create(wish, request);
		return Collections.singletonMap("message", "New wish added successfully");
	}

	@PostMapping(path = "/copy/userId/{userId}/id/{id}")
	public Map<String, String> copyWishFromUser(@PathVariable long userId, @PathVariable long id, HttpServletRequest request) throws Exception {
		wishService.copyWishFromUser(userId, id, request);

		return Collections.singletonMap("message", "true");
	}
	
	@PutMapping(path = "/{id}")
	public Map<String, String> update(@RequestBody Wish wish, @PathVariable long id, HttpServletRequest request) {
		wishService.modify(wish, id, request);

		return Collections.singletonMap("message", "Wish with name " + wish.getName() + " modifyed");
	}

	@DeleteMapping(path = "/{id}")
	public Map<String, String> delete(@PathVariable long id, HttpServletRequest request) {
		wishService.delete(id, request);

		return Collections.singletonMap("message", "Wish with id: " + id + " deleted");
	}
	
	@PutMapping("/wish_image/{id}")
	public Map<String, String> setImage(@PathVariable(value = "id") long id,
										@RequestParam("file") MultipartFile file, HttpServletRequest request)
			throws Exception {
		
		wishService.addImages(id, file, request);
		return Collections.singletonMap("message", "true");
	}
	
	//La 3ra es la vencida, wish imagen con google cloud storage
		@PostMapping(path = "/google_cloud_wish_image/{id}")
		public Wish uploadToGCP(@PathVariable(value = "id") long id, 
								@RequestParam("file") MultipartFile file, 
								HttpServletRequest request) throws Exception {
			
			return wishService.wishImageGoogleCloud(file, id, request);
		}
	
	@GetMapping(path = "/wish_image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void getWishImage(@PathVariable(value = "id") long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Wish wish = wishService.get(id, request);
		String wishPath = wish.getImagePath();
		
		Path file = Paths.get(wishPath);
		Resource imagFile = new UrlResource(file.toUri());
		
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(imagFile.getInputStream(), response.getOutputStream());
	}
   
}

