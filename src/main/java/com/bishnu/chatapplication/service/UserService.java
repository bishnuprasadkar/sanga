package com.bishnu.chatapplication.service;

import java.util.List;

import com.bishnu.chatapplication.dto.CurrentUserResponse;
import com.bishnu.chatapplication.dto.UpdateProfileRequest;
import com.bishnu.chatapplication.dto.UserSummaryResponse;
import com.bishnu.chatapplication.entity.User;
import com.bishnu.chatapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public CurrentUserResponse getCurrentUser(String username) {

        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

        return mapToCurrentUserResponse(user);
    }

    public CurrentUserResponse updateProfile(
        String username,
        UpdateProfileRequest request
    ) {

        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.displayName() != null) {
            user.setDisplayName(request.displayName());
        }

        if (request.profilePicture() != null) {
            user.setProfilePicture(request.profilePicture());
        }

        User savedUser = userRepository.save(user);

        return mapToCurrentUserResponse(savedUser);
    }

    @Transactional(readOnly = true)
    public List<UserSummaryResponse> searchUsers(
        String query,
        String currentUsername
    ) {

        return userRepository
            .findByUsernameContainingIgnoreCaseOrDisplayNameContainingIgnoreCase(
                query,
                query
            )
            .stream()
            .filter(user -> !user.getUsername().equalsIgnoreCase(currentUsername))
            .map(this::mapToUserSummaryResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public UserSummaryResponse getUserById(
        Long userId,
        String currentUsername
    ) {

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        return mapToUserSummaryResponse(user);
    }

    private CurrentUserResponse mapToCurrentUserResponse(User user) {

        return new CurrentUserResponse(
            user.getId(),
            user.getUsername(),
            user.getDisplayName(),
            user.getProfilePicture(),
            user.getOnlineStatus(),
            user.getLastSeen()
        );
    }

    private UserSummaryResponse mapToUserSummaryResponse(User user) {

        return new UserSummaryResponse(
            user.getId(),
            user.getUsername(),
            user.getDisplayName(),
            user.getProfilePicture(),
            user.getOnlineStatus(),
            user.getLastSeen()
        );
    }
}
