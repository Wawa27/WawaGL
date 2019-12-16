#version 450

const int MAX_INSTANCE_COUNT = 512;

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 normal;
layout(location = 2) in vec2 texCoord;

out vec2 aTexCoord;
out vec3 aNormal;
out vec3 aPosition;
out vec3 oColor;

out vec4 gl_Position;

layout(std140, binding = 0) uniform ViewProjection {
    mat4 view;
    mat4 projection;
};

uniform mat4 models[MAX_INSTANCE_COUNT];

void main() {
    vec4 world_position = models[gl_InstanceID] * vec4(position, 1.0);
    aPosition = vec3(view * world_position);
    aNormal = mat3(transpose(inverse(view * models[gl_InstanceID]))) * normal;
    aTexCoord = texCoord;

    oColor = vec3(.15, .15, .6);
    if (world_position.y > 0 && world_position.y < 13) {
        oColor = vec3(0, 1, 0);
    } else if (world_position.y >= 13) {
        oColor = vec3(1, 1, 1);
    }

    gl_Position = projection * view * world_position;
}
