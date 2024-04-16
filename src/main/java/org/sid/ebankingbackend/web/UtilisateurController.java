package org.sid.ebankingbackend.web;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.sid.ebankingbackend.dtos.user.UserUpdateDto;
import org.sid.ebankingbackend.dtos.user.model.*;
import org.sid.ebankingbackend.entities.User;
import org.sid.ebankingbackend.exceptions.exceptions.APIErrorException;
import org.sid.ebankingbackend.exceptions.exceptions.ApiError;
import org.sid.ebankingbackend.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "User", description = "APIs - Login - Get User Infos")
@ApiResponses({
		@ApiResponse(responseCode = "401", description = "Authentication is required to access the resource.", content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)) }),
		@ApiResponse(responseCode = "400", description = "The syntax of the request is incorrect.", content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)) }),
		@ApiResponse(responseCode = "404", description = "Resource not found.", content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)) }),
		@ApiResponse(responseCode = "500", description = "A system error occurred.", content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)) }),
		@ApiResponse(responseCode = "501", description = "Requested functionality is not supported by the server.", content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)) }) })
@RestController
@RequestMapping(path = "${URL-BASE}/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UtilisateurController {

	@Autowired
	UserService userService;
	@PostMapping("login")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Token Found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = TokenDto.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)) })
	})
	@Operation(summary = "Get Token", description = "Return Token access", method = "login", tags = {})
	public ResponseEntity<TokenDto> login(
			@Valid @RequestBody(required = true) LoginDto loginDto)
			throws APIErrorException {
		TokenDto oToken = userService.login(loginDto);

		return new ResponseEntity<>(oToken, HttpStatus.OK);
	}

	@PostMapping("/logout")
	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "Successfully logged out"),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)) })
	})
	@Operation(summary = "Logout", description = "Logout a user by providing the refresh token", tags = {})
	public void logout(@RequestParam("refreshToken") String refreshToken) throws APIErrorException {
		userService.logout(refreshToken);
	}

	@GetMapping("/refreshTokenAdmin")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Successfully retrieved admin refresh token"),
			@ApiResponse(responseCode = "401", description = "Authentication is required to access the resource.", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)) }),
			@ApiResponse(responseCode = "404", description = "Resource not found.", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)) })
	})
	@Operation(summary = "refesh access token", description = " refresh access token for admin user", tags = {})
	@PostMapping("/refresh-token")
	public ResponseEntity<TokenDto> refreshAccessToken(@RequestParam("refreshToken") String refreshToken) {
		try {
			TokenDto tokenDto = userService.refreshAccessToken(refreshToken);
			return ResponseEntity.ok(tokenDto);
		} catch (APIErrorException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/info")
	public UserDto getUserInfo() throws APIErrorException {
			UserDto userInfo = userService.getInfo();
			return userInfo;
    }



	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		//	try {
		List<User> userDtos = userService.getAllUser();
		return new ResponseEntity<>(userDtos, HttpStatus.OK);
		//	} catch (APIErrorException e) {
		//	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		//	}
	}

}
