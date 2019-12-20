#version 450

const int MAX_BONE_SIZE = 1024;

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 normal;
layout(location = 2) in vec2 texCoord;

layout(location = 3) in vec4 weights;
layout(location = 4) in ivec4 ids;

out vec2 oTexCoord;
out vec3 oNormal;
out vec3 oPosition;
out vec3 oColor;

out vec4 gl_Position;

layout(std140, binding = 0) uniform ViewProjection {
    mat4 view;
    mat4 projection;
};

uniform mat4 model;
uniform mat4 armature[MAX_BONE_SIZE];

void main() {
    mat4 boneTransform = weights.r * armature[ids.r];
    boneTransform += weights.g * armature[ids.g];
    boneTransform += weights.b * armature[ids.b];
    boneTransform += weights.a * armature[ids.a];
    vec4 localSpacePosition = boneTransform * vec4(position, 1.0);

    oPosition = vec3(view * model * localSpacePosition);
    oNormal = normalize(transpose(inverse(mat3(view * model))) * normal);
    oTexCoord = texCoord;

    gl_Position = projection * view * model * localSpacePosition;
}
